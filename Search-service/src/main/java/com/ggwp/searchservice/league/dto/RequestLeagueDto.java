package com.ggwp.searchservice.league.dto;

import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.summoner.domain.Summoner;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLeagueDto {

    @NotBlank(message = "leagueId는 공백이 될 수 없습니다.")
    private String leagueId;

    @NotBlank(message = "queueType은 공백이 될 수 없습니다.")
    private String queueType;

    @NotBlank(message = "tier는 공백이 될 수 없습니다.")
    private String tier;
    @NotBlank(message = "rank는 공백이 될 수 없습니다.")
    private String rank;
    @NotBlank(message = "leaguePoints는 공백이 될 수 없습니다.")
    private int leaguePoints;
    @NotBlank(message = "wins는 공백이 될 수 없습니다.")
    private int wins;
    @NotBlank(message = "losses는 공백이 될 수 없습니다.")
    private int losses;

    private Summoner summoner;

    public League toEntity(Summoner summoner) {
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
