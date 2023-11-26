package com.ggwp.searchservice.summoner.dto;

import com.ggwp.searchservice.summoner.domain.Summoner;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCreateSummonerDto {

    private String id;

    private int profileIconId;

    private String puuid;

    private String name;

    private Long revisionDate;

    private int summonerLevel;

    //    private String accountId;

    // DTO를 -> Entity 로 변경
    public Summoner toEntity() {
        return Summoner.builder()
                .id(this.id)
                .profileIconId(this.profileIconId)
                .puuid(this.puuid)
                .name(this.name)
                .revisionDate(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
                .summonerLevel(this.summonerLevel)
                .build();
    }
}
