package com.ggwp.searchservice.league.dto;

import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.summoner.domain.Summoner;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseFindLeagueDto {

    private String leagueId;

    private String queueType;

    private String tier;

    private String rank;

    private int leaguePoints;

    private int wins;

    private int losses;

    private Summoner summoner;

    public static ResponseFindLeagueDto toDto(League league) {
        return ResponseFindLeagueDto.builder()
                .leagueId(league.getLeagueId())
                .queueType(league.getQueueType())
                .tier(league.getTier())
                .rank(league.getRanks())
                .leaguePoints(league.getLeaguePoints())
                .wins(league.getWins())
                .losses(league.getLosses())
                .summoner(league.getSummoner())
                .build();
    }

}
