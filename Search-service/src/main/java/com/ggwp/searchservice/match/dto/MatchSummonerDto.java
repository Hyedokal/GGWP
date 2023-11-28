package com.ggwp.searchservice.match.dto;

import com.ggwp.searchservice.match.domain.MatchSummoner;
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

    public static MatchSummonerDto toDto(MatchSummoner matchSummoner) {
        return MatchSummonerDto.builder()
                .matchId(matchSummoner.getMatch().getMatchId())
                .summonerId(matchSummoner.getSummoner().getId())
                // 기타 필요한 필드 설정
                .build();
    }
}

