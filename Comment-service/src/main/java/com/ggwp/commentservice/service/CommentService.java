package com.ggwp.commentservice.service;

import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.dto.response.ResponseCommentDto;

import java.util.List;

public interface CommentService {
    public void writeComment(RequestCommentDto dto);

    public void editComment(Long cId, RequestCommentDto dto);

    public void deleteComment(Long cId);

    public List<ResponseCommentDto> getCommentList(Long sId);
}
