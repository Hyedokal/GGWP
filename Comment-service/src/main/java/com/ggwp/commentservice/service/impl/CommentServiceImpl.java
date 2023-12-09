package com.ggwp.commentservice.service.impl;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.domain.QComment;
import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.dto.request.RequestPageDto;
import com.ggwp.commentservice.dto.response.ResponseCommentDto;
import com.ggwp.commentservice.exception.ErrorMsg;
import com.ggwp.commentservice.repository.CommentRepository;
import com.ggwp.commentservice.service.CommentService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EntityManager entityManager;

    //댓글 작성 후 저장하기
    public Comment writeComment(RequestCommentDto dto) {
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

    //게시글에 달린 댓글 목록 조회하기
    @Transactional(readOnly = true)
    public List<ResponseCommentDto> getCommentList(Long sId) {
        List<Comment> commentList = commentRepository.findAllBysId(sId);
        return commentList.stream().map(ResponseCommentDto::fromEntity).toList();
    }

    //댓글 상세 조회하기
    public ResponseCommentDto getOneComment(Long cId) {
        Comment comment = commentRepository.findById(cId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.COMMENT_NOT_FOUND));
        return ResponseCommentDto.fromEntity(comment);
    }

    public Page<ResponseCommentDto> searchPagedComment(RequestPageDto.Search dto){
        QComment qComment =QComment.comment;
        BooleanBuilder where = where();
        if (dto.getSId() != null) {
            where.and(qComment.sId.eq(dto.getSId()));
        }
        Long total = query().select(qComment.count())
                .from(qComment)
                .where(where)
                .fetchOne();
        total = (total == null)? 0 : total;
        List<ResponseCommentDto> dtoList = new ArrayList<>();

        if(total > 0){
            dtoList = query().select(qComment)
                    .from(qComment)
                    .where(where)
                    .orderBy(qComment.createdAt.desc()) // 생성일자 내림차순
                    .offset((long) dto.getPage() * dto.getSize())
                    .limit(dto.getSize())
                    .fetch()
                    .stream()
                    .map(ResponseCommentDto::fromEntity)
                    .toList();
        }
        return new PageImpl<>(dtoList, PageRequest.of(dto.getPage(), dto.getSize()), total);
    }
    //페이징처리된 댓글 조회하기

    private BooleanBuilder where() {
        return new BooleanBuilder();
    }

    private JPAQueryFactory query() {
        return new JPAQueryFactory(entityManager);
    }
}
