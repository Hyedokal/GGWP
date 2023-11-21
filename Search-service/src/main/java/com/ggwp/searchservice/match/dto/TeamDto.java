package com.ggwp.searchservice.match.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ggwp.searchservice.match.domain.Team;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDto {

    @JsonProperty("teamId")
    private int teamId;

    @JsonProperty("win")
    private boolean win;
    public Team toEntity() {
        return Team.builder()
                .teamId(this.teamId)
                .win(this.win)
                .build();
    }
}