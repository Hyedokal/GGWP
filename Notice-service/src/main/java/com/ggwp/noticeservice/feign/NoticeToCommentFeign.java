package com.ggwp.noticeservice.feign;

import com.ggwp.noticeservice.dto.ResponseCommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(name = "comment-service")
public interface NoticeToCommentFeign {

        @GetMapping("/v1/comments/feign/{cId}") // commentId를 통해 커멘트 가져오기
        ResponseCommentDto getComment(@PathVariable Long cId);
}

