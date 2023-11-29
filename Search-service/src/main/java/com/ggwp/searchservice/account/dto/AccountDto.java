package com.ggwp.searchservice.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.summoner.domain.Summoner;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    @JsonProperty("puuid")
    private String puuid;
    @JsonProperty("gameName")
    private String gameName;
    @JsonProperty("tagLine")
    private String tagLine;

    public Account toEntity(Summoner summoner) {
        return Account.builder()
                .puuid(this.puuid)
                .gameName(this.gameName)
                .tagLine(this.tagLine)
                .summoner(summoner)
                .build();
    }
}
