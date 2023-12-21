package com.ggwp.commentservice.feign.squad;

import com.ggwp.commentservice.dto.memberFeign.request.RequestSidDto;
import com.ggwp.commentservice.dto.memberFeign.response.ResponseFeignSquadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "Squad-service")
public interface SquadFeign {

    @PostMapping("v1/squads/feign/comment/matcher")
    ResponseEntity<List<ResponseFeignSquadDto>> getCommentMatch(@RequestBody RequestSidDto requestDto) ;
}