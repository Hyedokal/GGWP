package com.ggwp.squadservice.feign;

import com.ggwp.squadservice.dto.response.ResponseCommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "comment-service")
public interface CommentFeignClient {
    @GetMapping("/v1/comments/{sId}")
    public List<ResponseCommentDto> getCommentList(@PathVariable Long sId);
}
