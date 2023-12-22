package com.ggwp.squadservice.feign;

import com.ggwp.squadservice.dto.memberfeign.request.FeignLolNickNameTagRequestDto;
import com.ggwp.squadservice.dto.memberfeign.response.FeignLolNickNameTagResponseDto;
import com.ggwp.squadservice.dto.request.RequestCommentPageDto;
import com.ggwp.squadservice.dto.response.ResponseCommentDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Comment-service", url = "http://GGWP-LoadBalancer-298705153.ap-northeast-2.elb.amazonaws.com:80")
public interface CommentFeignClient {
    @GetMapping("/v1/comments/search")
    Page<ResponseCommentDto> getPagedComment(@RequestBody RequestCommentPageDto.Search dto);

    @PutMapping("/v1/comments/feign/{cId}")
    ResponseEntity<String> approveComment(@PathVariable Long cId);


    @PutMapping("/v1/comments/lolNickname-tag")
    ResponseEntity<FeignLolNickNameTagResponseDto> FeignLolNickNameTag(@RequestBody @Valid FeignLolNickNameTagRequestDto requestBody);


}
