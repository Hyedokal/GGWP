package com.ggwp.commentservice.service.impl;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.dto.response.ResponseCommentDto;
import com.ggwp.commentservice.exception.ErrorMsg;
import com.ggwp.commentservice.repository.CommentRepository;
import com.ggwp.commentservice.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    public CommentRepository commentRepository;

    //댓글 작성 후 저장하기
    public void writeComment(RequestCommentDto dto) {
        Comment comment = dto.toEntity();
        commentRepository.save(comment);
    }

    //댓글 수정하기
    public void editComment(Long cId, RequestCommentDto dto) {
        Comment comment = commentRepository.findById(cId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.COMMENT_NOT_FOUND));
        comment.updateComment(dto);
        commentRepository.save(comment);
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
}
