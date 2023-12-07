package com.ggwp.searchservice.match.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchSummonerDto {
    private String matchId;
    private String summonerId;
}

