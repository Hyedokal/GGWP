package com.ggwp.searchservice.league.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateLeagueDto {

    @JsonProperty("leagueId")
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

    @JsonProperty("summonerId")
    private String summonerId;

    @JsonProperty("summonerName")
    private String summonerName;

}
