package com.ggwp.squadservice.service.impl;

import com.ggwp.squadservice.domain.QSquad;
import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.dto.request.RequestCommentPageDto;
import com.ggwp.squadservice.dto.request.RequestSquadDto;
import com.ggwp.squadservice.dto.request.RequestSquadPageDto;
import com.ggwp.squadservice.dto.response.ResponseCommentDto;
import com.ggwp.squadservice.dto.response.ResponseSquadDto;
import com.ggwp.squadservice.dto.response.riotapi.LeagueEntryDTO;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import com.ggwp.squadservice.enums.RomanNum;
import com.ggwp.squadservice.enums.Tier;
import com.ggwp.squadservice.exception.ErrorMsg;
import com.ggwp.squadservice.feign.CommentFeignClient;
import com.ggwp.squadservice.feign.RiotFeignClient;
import com.ggwp.squadservice.repository.SquadRepository;
import com.ggwp.squadservice.service.SquadService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import feign.FeignException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SquadServiceImpl implements SquadService {

    private final SquadRepository squadRepository;
    private final CommentFeignClient commentFeignClient;
    private final EntityManager entityManager;
    private final RiotFeignClient riotFeignClient;

    @Value("${apiKey}")
    private String apiKey;

    //롤 닉네임을 통해 티어값을 받아 오는 메서드
    public Map<QType, String> getSummonerRank(String summonerName) {
        Map<QType, String> rankMap = new HashMap<>();
        try {
            String summonerId = riotFeignClient.getSummonerId(summonerName, apiKey).getId();
            Set<LeagueEntryDTO> rankInfo = riotFeignClient.getRankInfo(summonerId, apiKey);

            for (LeagueEntryDTO dto : rankInfo) {
                String queueType = dto.getQueueType();
                String rank = dto.getRank();
                String tier = dto.getTier();

                if (queueType.equals("RANKED_SOLO_5x5")) {
                    String rankString = Tier.getAbbreviationByFullName(tier) + RomanNum.getValueByRomanNum(rank);
                    rankMap.put(QType.SOLO_RANK, rankString);
                } else if (queueType.equals("RANKED_FLEX_SR")) {
                    String rankString = Tier.getAbbreviationByFullName(tier) + RomanNum.getValueByRomanNum(rank);
                    rankMap.put(QType.FLEX_RANK, rankString);
                }
            }
        } catch (FeignException e) {
            log.info("Error: " + e.getMessage());
            rankMap.put(QType.SOLO_RANK, "error-issue");
            rankMap.put(QType.FLEX_RANK, "error-issue");
        }


        return rankMap;
    }

    //게시글 작성 후 저장하기
    public Squad writeSquad(RequestSquadDto dto) {
        Map<QType, String> summonerRanks = this.getSummonerRank(dto.getSummonerName());
        String summonerRank = summonerRanks.getOrDefault(dto.getQType(), "Unranked");

        if ("error-issue".equals(summonerRank)) {
            dto.setSummonerRank("Unknown Rank");
        } else if (dto.getQType().equals(QType.SOLO_RANK)) {
            dto.setSummonerRank(this.getSummonerRank(dto.getSummonerName()).get(QType.SOLO_RANK));
        } else if (dto.getQType().equals(QType.FLEX_RANK)) {
            dto.setSummonerRank(this.getSummonerRank(dto.getSummonerName()).get(QType.FLEX_RANK));
        }

        Squad squad = dto.toEntity();
        return squadRepository.save(squad);
    }

    //게시글 수정하기
    public Squad editSquad(Long sId, RequestSquadDto dto) {
        Squad squad = squadRepository.findById(sId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.SQUAD_ID_NOT_FOUND));
        squad.updateSquad(dto);
        return squadRepository.save(squad);
    }

    //게시글 삭제하기
    public void deleteSquad(Long sId) {
        Squad squad = squadRepository.findById(sId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.SQUAD_ID_NOT_FOUND));
        squadRepository.delete(squad);
    }

    //게시글 전체 조회하기 (페이징 처리)
    @Transactional(readOnly = true)
    public Page<ResponseSquadDto> searchPagedSquad(RequestSquadPageDto.Search dto) {
        QSquad qSquad = QSquad.squad;
        BooleanBuilder where = where();
        //outdated == false 조건을 추가
        if (!dto.isOutdated()) {
            where.and(qSquad.outdated.eq(false));
        }
        Long total = query().select(qSquad.count())
                .from(qSquad)
                .where(where)
                .fetchOne();
        total = (total == null) ? 0 : total;
        List<ResponseSquadDto> dtoList = new ArrayList<>();

        if (total > 0) {
            dtoList = query().select(qSquad)
                    .from(qSquad)
                    .where(where)
                    .orderBy(qSquad.sId.desc()) // PK 내림차순
                    .offset((long) dto.getPage() * dto.getSize())
                    .limit(dto.getSize())
                    .fetch()
                    .stream()
                    .map(ResponseSquadDto::fromEntity)
                    .toList();
        }
        return new PageImpl<>(dtoList, PageRequest.of(dto.getPage(), dto.getSize()), total);
    }

    //게시글 상세 조회하기
    @Transactional(readOnly = true)
    public ResponseSquadDto getOneSquad(Long sId) {
        Squad squad = squadRepository.findById(sId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.SQUAD_ID_NOT_FOUND));
        ResponseSquadDto dto = ResponseSquadDto.fromEntity(squad);
        //Feign으로 댓글 리스트 가져오기
        Page<ResponseCommentDto> comments = commentFeignClient.getPagedComment(new RequestCommentPageDto.Search());
        dto.setCommentList(comments);
        return dto;
    }

    //게시글 필터 별 조회하기
    @Transactional(readOnly = true)
    public List<ResponseSquadDto> getSquadWithFilters(Position myPos, QType qType, String rank) {
        Specification<Squad> spec = Specification.where(null);

        spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("outdated"), false));

        if (myPos != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("myPos"), myPos));
        }

        if (qType != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("qType"), qType));
        }

        if (rank != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("summonerRank"), rank));
        }

        List<Squad> squadList = squadRepository.findAll(spec);
        return squadList.stream().map(ResponseSquadDto::fromEntity).toList();
    }

    private BooleanBuilder where() {
        return new BooleanBuilder();
    }

    private JPAQueryFactory query() {
        return new JPAQueryFactory(entityManager);
    }
}
