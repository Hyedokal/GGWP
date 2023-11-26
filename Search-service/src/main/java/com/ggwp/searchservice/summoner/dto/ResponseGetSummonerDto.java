package com.ggwp.searchservice.summoner.dto;

import com.ggwp.searchservice.summoner.domain.Summoner;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseGetSummonerDto {

    private String id;

    private int profileIconId;

//    private String accountId;

    private String puuid;

    private String name;

    private Long revisionDate;

    private int summonerLevel;

    // DTO를 -> Entity 로 변경
    public Summoner toEntity() {
        return Summoner.builder()
                .id(this.id)
                .profileIconId(this.profileIconId)
//                .accountId(this.accountId)
                .puuid(this.puuid)
                .name(this.name)
                .revisionDate(this.revisionDate)
                .summonerLevel(this.summonerLevel)
                .build();
    }
}
