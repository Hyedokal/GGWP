package com.ggwp.searchservice.match.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoDto {
    @JsonProperty("gameId")
    private Long gameId;

    @JsonProperty("gameCreation")
    private Long gameCreation;
    @JsonProperty("gameDuration")
    private long gameDuration; // 게임 지속 시각

    @JsonProperty("gameEndTimestamp")
    private long gameEndTimestamp; // 게임 종료 시각

    @JsonProperty("gameStartTimestamp")
    private long gameStartTimestamp; // 게임 시작 시각
    @JsonProperty("participants")
    List<ParticipantDto> participants;

    @JsonProperty("platformId")
    private String platformId;

    @JsonProperty("queueId")
    private Integer queueId;

    @JsonProperty("teams")
    private List<TeamDto> teams;
}
