package com.ggwp.commentservice.service;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.dto.memberFeign.request.RequestFeignSquadDto;
import com.ggwp.commentservice.dto.memberFeign.request.RequestMatchDto;
import com.ggwp.commentservice.dto.memberFeign.response.ResponseFeignSquadDto;
import com.ggwp.commentservice.dto.memberFeign.response.ResponseMatchDto;
import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.dto.request.RequestPageDto;
import com.ggwp.commentservice.dto.response.ResponseCommentDto;
import com.ggwp.commentservice.dto.squadFeign.request.FeignLolNickNameTagRequestDto;
import com.ggwp.commentservice.dto.squadFeign.response.FeignLolNickNameTagResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    Comment writeComment(RequestCommentDto dto);

    Comment editComment(Long cId, RequestCommentDto dto);

    void deleteComment(Long cId);

    List<ResponseMatchDto> getSummonerDetails(RequestMatchDto requestDto);

    ResponseCommentDto getOneComment(Long cId);


    Page<ResponseCommentDto> searchPagedComment(RequestPageDto.Search dto);

    Comment approveComment(Long cId);


    ResponseEntity<FeignLolNickNameTagResponseDto> patchLolNickTag(FeignLolNickNameTagRequestDto requestBody);


    List<ResponseFeignSquadDto> getCommentMatch(RequestFeignSquadDto requestDto);
}
