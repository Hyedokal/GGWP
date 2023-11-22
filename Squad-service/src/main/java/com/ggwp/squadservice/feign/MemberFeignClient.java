package com.ggwp.squadservice.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "member-service")
public interface MemberFeignClient {

}
