package com.ggwp.searchservice.league.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseLeagueDto {

    private String leagueId;

    private String queueType;

    private String tier;

    private String rank;

    private int leaguePoints;

    private int wins;

    private int losses;

    private String summonerId;

    private String summonerName;

}
