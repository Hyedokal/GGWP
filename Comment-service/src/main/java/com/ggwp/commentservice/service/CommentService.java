package com.ggwp.commentservice.service;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.dto.RequestCommentDto;
import com.ggwp.commentservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {

    @Autowired
    public CommentRepository commentRepository;

    //댓글 작성 후 저장하기
    public void writeComment(RequestCommentDto dto){
        Comment comment = dto.toEntity();
        commentRepository.save(comment);
    }

    //댓글 수정하기
    @Transactional
    public void editComment(Long cId, RequestCommentDto dto){
        Comment comment = commentRepository.findById(cId)
                .orElseThrow(() -> new NoSuchElementException("해당 댓글이 없습니다."));
        comment.updateComment(dto);
        commentRepository.save(comment);
    }

    //댓글 삭제하기
    @Transactional
    public void deleteComment(Long cId){
        Comment comment = commentRepository.findById(cId)
                .orElseThrow(() -> new NoSuchElementException("해당 댓글이 없습니다."));
        commentRepository.delete(comment);
    }

    //게시글별 댓글 조회
    public List<Comment> listCommentBySquad(Long sId){
        return commentRepository.findBySquad(sId);
    }
}
