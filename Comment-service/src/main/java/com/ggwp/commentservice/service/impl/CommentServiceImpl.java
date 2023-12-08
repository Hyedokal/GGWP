package com.ggwp.commentservice.service.impl;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.dto.response.ResponseCommentDto;
import com.ggwp.commentservice.exception.ErrorMsg;
import com.ggwp.commentservice.repository.CommentRepository;
import com.ggwp.commentservice.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

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
}
