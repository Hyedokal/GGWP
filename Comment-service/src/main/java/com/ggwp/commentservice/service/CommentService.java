package com.ggwp.commentservice.service;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.dto.request.RequestPageDto;
import com.ggwp.commentservice.dto.response.ResponseCommentDto;
import org.springframework.data.domain.Page;

public interface CommentService {
    Comment writeComment(RequestCommentDto dto);

    Comment editComment(Long cId, RequestCommentDto dto);

    void deleteComment(Long cId);

    ResponseCommentDto getOneComment(Long cId);

    Page<ResponseCommentDto> searchPagedComment(RequestPageDto.Search dto);

    Comment approveComment(Long cId);
}
