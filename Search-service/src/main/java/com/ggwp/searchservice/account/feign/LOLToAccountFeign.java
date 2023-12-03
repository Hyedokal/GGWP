package com.ggwp.searchservice.account.feign;

import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "lol-account", url = "https://asia.api.riotgames.com/riot/")
public interface LOLToAccountFeign {
    @GetMapping("account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
    Optional<ResponseAccountDto> getAccount(
            @PathVariable String gameName,
            @PathVariable String tagLine,
            @RequestParam("api_key") String apiKey
    );

}
