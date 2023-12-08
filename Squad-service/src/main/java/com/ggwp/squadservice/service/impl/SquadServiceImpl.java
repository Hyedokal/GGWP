package com.ggwp.squadservice.service.impl;

import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.dto.request.RequestSquadDto;
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
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SquadServiceImpl implements SquadService {

    @Autowired
    private final SquadRepository squadRepository;
    private final CommentFeignClient commentFeignClient;
    private final RiotFeignClient riotFeignClient;

    @Value("${apiKey}")
    private String apiKey;

    //롤 닉네임을 통해 티어값을 받아오는 메서드
    public Map<QType, String> getSummonerRank(String summonerName) {
        Map<QType, String> rankMap = new HashMap<>();
        try {
            String summonerId = riotFeignClient.getSummonerId(summonerName, apiKey).getId();
            Set<LeagueEntryDTO> rankInfo = riotFeignClient.getRankInfo(summonerId, apiKey);

            for (LeagueEntryDTO dto : rankInfo) {
                String queueType = dto.getQueueType();
                String rank = dto.getRank();
                String tier = dto.getTier();

                // Print statements for debugging
                System.out.println("Queue Type: " + queueType);
                System.out.println("Rank: " + rank);
                System.out.println("Tier: " + tier);

                if (queueType.equals("RANKED_SOLO_5x5")) {
                    String rankString = Tier.getAbbreviationByFullName(tier) + RomanNum.getValueByRomanNum(rank);
                    System.out.println("Computed Rank String for Solo Queue: " + rankString);
                    rankMap.put(QType.SOLO_RANK, rankString);
                } else if (queueType.equals("RANKED_FLEX_SR")) {
                    String rankString = Tier.getAbbreviationByFullName(tier) + RomanNum.getValueByRomanNum(rank);
                    System.out.println("Computed Rank String for Flex Queue: " + rankString);
                    rankMap.put(QType.FLEX_RANK, rankString);
                }
            }
        } catch (FeignException e) {
            System.out.println("Error: " + e.getMessage());
            rankMap.put(QType.SOLO_RANK, "error-issue");
            rankMap.put(QType.FLEX_RANK, "error-issue");
        }



        return rankMap;
    }

    //게시글 작성 후 저장하기
    public void writeSquad(RequestSquadDto dto) {
        Map<QType, String> summonerRanks = this.getSummonerRank(dto.getSummonerName());// 현재 라이엇 측에서 api 호출 버그가있어 랭크 정보를 가져오지 못하는 경우가 있음 -jongha summoner_rank
        String summonerRank = summonerRanks.getOrDefault(dto.getQType(), "Unknown Rank");

        if ("error-issue".equals(summonerRank)) {
            summonerRank = "Unknown Rank";
        }else if (dto.getQType().equals(QType.SOLO_RANK)) {
            dto.setSummonerRank(this.getSummonerRank(dto.getSummonerName()).get(QType.SOLO_RANK));
        } else if (dto.getQType().equals(QType.FLEX_RANK)) {
            dto.setSummonerRank(this.getSummonerRank(dto.getSummonerName()).get(QType.FLEX_RANK));
        }

        dto.setSummonerRank(summonerRank);
        Squad squad = dto.toEntity();
        squadRepository.save(squad);
    }

    //게시글 수정하기
    public void editSquad(Long sId, RequestSquadDto dto) {
        Squad squad = squadRepository.findById(sId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.SQUAD_ID_NOT_FOUND));
        squad.updateSquad(dto);
        squadRepository.save(squad);
    }

    //게시글 삭제하기
    public void deleteSquad(Long sId) {
        Squad squad = squadRepository.findById(sId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.SQUAD_ID_NOT_FOUND));
        squadRepository.delete(squad);
    }

    //게시글 전체 조회하기
    @Transactional(readOnly = true)  //todo: DESC 하는게어떨까요 ㅎㅎ - 종하
    public List<ResponseSquadDto> getAllSquad() {
        List<Squad> squadList = squadRepository.findAll();
        return squadList.stream()
                .map(ResponseSquadDto::fromEntity)
                .toList();
    }

    //게시글 상세 조회하기
    @Transactional(readOnly = true)
    public ResponseSquadDto getOneSquad(Long sId) {
        Squad squad = squadRepository.findById(sId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.SQUAD_ID_NOT_FOUND));
        ResponseSquadDto.fromEntity(squad);
        //Feign으로 댓글 리스트 가져오기
        List<ResponseCommentDto> commentList = commentFeignClient.getCommentList(sId);
        ResponseSquadDto dto = ResponseSquadDto.fromEntity(squad);
        dto.setCommentList(commentList);
        return dto;
    }

    //게시글 필터 별 조회하기
    @Transactional(readOnly = true)
    public List<ResponseSquadDto> getSquadWithFilters(Position myPos, QType qType, String rank) {
        Specification<Squad> spec = Specification.where(null);

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
                    criteriaBuilder.equal(root.get("rank"), rank));
        }

        List<Squad> squadList = squadRepository.findAll(spec);
        return squadList.stream().map(ResponseSquadDto::fromEntity).toList();
    }



}
