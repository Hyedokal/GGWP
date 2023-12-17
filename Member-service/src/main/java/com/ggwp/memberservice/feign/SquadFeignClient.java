package com.ggwp.memberservice.feign;


import com.ggwp.memberservice.dto.feign.request.RequestMatchDto;
import com.ggwp.memberservice.dto.feign.response.ResponseMatchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "squad-service")
public interface SquadFeignClient {

    @PostMapping("v1/squads/match/list")
    ResponseEntity<ResponseMatchDto> getMatchInfo(@RequestBody RequestMatchDto dto);



}
