package com.ggwp.searchservice.league.dto;

import com.ggwp.searchservice.enums.GameMode;
import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.dto.ResponseGetSummonerDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseGetLeagueDto {

    private String leagueId;

    private String queueType;

    private String tier;

    private String rank;

    private int leaguePoints;

    private int wins;

    private int losses;

    private ResponseGetSummonerDto summoner;
    public League toEntity(Summoner summoner){
        return League.builder()
                .leagueId(this.leagueId)
                .queueType(this.queueType)
                .tier(this.tier)
                .ranks(this.rank)
                .leaguePoints(this.leaguePoints)
                .wins(this.wins)
                .losses(this.losses)
                .summoner(summoner)
                .build();
    }

}
