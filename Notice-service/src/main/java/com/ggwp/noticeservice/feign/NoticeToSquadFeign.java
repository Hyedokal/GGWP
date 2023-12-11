package com.ggwp.noticeservice.feign;

import com.ggwp.noticeservice.dto.ResponseCommentDto;
import com.ggwp.noticeservice.dto.ResponseSquadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sqaud-service",url = "http://localhost:8002/v1/squads")
public interface NoticeToSquadFeign {

    @GetMapping("/{sId}") // commentId를 통해 커멘트 가져오기
    ResponseSquadDto getOneSquad(@PathVariable Long sId);
}




