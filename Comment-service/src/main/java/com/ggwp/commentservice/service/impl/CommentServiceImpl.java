package com.ggwp.commentservice.service.impl;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.domain.QComment;
import com.ggwp.commentservice.dto.memberFeign.request.RequestMatchDto;
import com.ggwp.commentservice.dto.memberFeign.response.ResponseMatchDto;
import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.dto.request.RequestPageDto;
import com.ggwp.commentservice.dto.response.ResponseCommentDto;
import com.ggwp.commentservice.dto.response.riotapi.LeagueEntryDTO;
import com.ggwp.commentservice.dto.squadFeign.request.FeignLolNickNameTagRequestDto;
import com.ggwp.commentservice.dto.squadFeign.response.FeignLolNickNameTagResponseDto;
import com.ggwp.commentservice.enums.QType;
import com.ggwp.commentservice.enums.RomanNum;
import com.ggwp.commentservice.enums.Tier;
import com.ggwp.commentservice.exception.ErrorMsg;
import com.ggwp.commentservice.feign.RiotFeignClient;
import com.ggwp.commentservice.repository.CommentRepository;
import com.ggwp.commentservice.service.CommentService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
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

    //댓글 작성 후 저장하기
    public Comment writeComment(RequestCommentDto dto) {
        Map<QType, String> summonerRanks = this.getSummonerRank(dto.getSummonerName());
        //칼바람의 경우 무조건 언랭이며, 솔랭/자랭이 언랭일경우 Map 객체에 랭크 정보가 들어 있지 않음
        String summonerRank = summonerRanks.getOrDefault(dto.getQType(), "Unranked");

        if ("error-issue".equals(summonerRank)) {
            dto.setSummonerRank("Unknown Rank");
        } else if (dto.getQType().equals(QType.SOLO_RANK)) {
            dto.setSummonerRank(this.getSummonerRank(dto.getSummonerName()).get(QType.SOLO_RANK));
        } else if (dto.getQType().equals(QType.FLEX_RANK)) {
            dto.setSummonerRank(this.getSummonerRank(dto.getSummonerName()).get(QType.FLEX_RANK));
        }

        Comment comment = dto.toEntity();
        return commentRepository.save(comment);
    }

    //댓글 수정하기
    public Comment editComment(Long cId, RequestCommentDto dto) {
        Comment comment = commentRepository.findById(cId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.COMMENT_NOT_FOUND));
        comment.updateComment(dto);
        return commentRepository.save(comment);
    }

    //댓글 삭제하기
    public void deleteComment(Long cId) {
        Comment comment = commentRepository.findById(cId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }



    @Override
    public List<ResponseMatchDto> getSummonerDetails(RequestMatchDto requestDto) {
        List<ResponseMatchDto> responseList = new ArrayList<>();

        for (Long sId : requestDto.getIds()) {
            List<Comment> comments = commentRepository.findAllBysId(sId);

            for (Comment comment : comments) {
                if (comment != null) {
                    responseList.add(new ResponseMatchDto(comment.getSId(),comment.getSummonerName(), comment.getTagLine()));
                }
            }
        }

        return responseList;
    }




    //댓글 상세 조회하기
    public ResponseCommentDto getOneComment(Long cId) {
        Comment comment = commentRepository.findById(cId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.COMMENT_NOT_FOUND));
        return ResponseCommentDto.fromEntity(comment);
    }

    //댓글 승인 버튼 눌렀을 때의 로직
    public Comment approveComment(Long cId) {
        Comment comment = commentRepository.findById(cId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.COMMENT_NOT_FOUND));
        RequestCommentDto dto = comment.toDto();
        dto.setApproved(true);
        comment.updateComment(dto);
        return commentRepository.save(comment);
    }


    public Page<ResponseCommentDto> searchPagedComment(RequestPageDto.Search dto) {
        QComment qComment = QComment.comment;
        BooleanBuilder where = where();
        //게시글 PK인 sId로 검색
        if (dto.getSId() != null) {
            where.and(qComment.sId.eq(dto.getSId()));
        }
        Long total = query().select(qComment.count())
                .from(qComment)
                .where(where)
                .fetchOne();
        total = (total == null) ? 0 : total;
        List<ResponseCommentDto> dtoList = new ArrayList<>();

        if (total > 0) {
            dtoList = query().select(qComment)
                    .from(qComment)
                    .where(where)
                    .orderBy(qComment.cId.desc()) // PK 내림차순
                    .offset((long) dto.getPage() * dto.getSize())
                    .limit(dto.getSize())
                    .fetch()
                    .stream()
                    .map(ResponseCommentDto::fromEntity)
                    .toList();
        }
        return new PageImpl<>(dtoList, PageRequest.of(dto.getPage(), dto.getSize()), total);
    }

    private BooleanBuilder where() {
        return new BooleanBuilder();
    }

    private JPAQueryFactory query() {
        return new JPAQueryFactory(entityManager);
    }



    @Override
    public ResponseEntity<FeignLolNickNameTagResponseDto> patchLolNickTag(FeignLolNickNameTagRequestDto requestBody) {
        try{
            List<Comment> comments = commentRepository.findBySummonerNameAndTagLine(requestBody.getExistLolNickName(), requestBody.getExistTag());

            for(Comment comment : comments){
                comment.setSummonerName(requestBody.getLolNickName());
                comment.setTagLine(requestBody.getTag());
                commentRepository.save(comment);
            }
            return ResponseEntity.ok().body(new FeignLolNickNameTagResponseDto(true, "Squad data updated successfully."));
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new FeignLolNickNameTagResponseDto(false, "An error occurred while updating squad data."));
        }

    }
}
