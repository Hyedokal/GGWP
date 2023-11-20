package com.ggwp.squadservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "member-service")
public interface MemberFeignClient {

    @GetMapping("/")
}
