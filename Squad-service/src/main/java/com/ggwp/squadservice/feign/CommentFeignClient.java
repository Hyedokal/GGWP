package com.ggwp.squadservice.feign;

import com.ggwp.squadservice.domain.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "comment-service")
public interface CommentFeignClient {
    @GetMapping("/v1/comment/{sId}")
    public List<Comment> getCommentList(@PathVariable Long sId);
}
