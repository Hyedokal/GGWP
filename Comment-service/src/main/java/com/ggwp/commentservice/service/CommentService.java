package com.ggwp.commentservice.service;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.dto.request.RequestPageDto;
import com.ggwp.commentservice.dto.response.ResponseCommentDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    public Comment writeComment(RequestCommentDto dto);

    public Comment editComment(Long cId, RequestCommentDto dto);

    public void deleteComment(Long cId);

    public List<ResponseCommentDto> getCommentList(Long sId);

    public ResponseCommentDto getOneComment(Long cId);

    Page<ResponseCommentDto> searchPagedComment(RequestPageDto.Search dto);
}
