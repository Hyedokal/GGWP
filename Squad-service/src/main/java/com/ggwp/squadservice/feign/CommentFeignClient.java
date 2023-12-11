package com.ggwp.squadservice.feign;

import com.ggwp.squadservice.dto.request.RequestCommentPageDto;
import com.ggwp.squadservice.dto.response.ResponseCommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "comment-service")
public interface CommentFeignClient {
    @GetMapping("/v1/comments/search")
    Page<ResponseCommentDto> getPagedComment(@RequestBody RequestCommentPageDto.Search dto);
}
