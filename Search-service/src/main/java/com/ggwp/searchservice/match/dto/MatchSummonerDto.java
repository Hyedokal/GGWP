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
    // 기타 필요한 필드 추가

}

