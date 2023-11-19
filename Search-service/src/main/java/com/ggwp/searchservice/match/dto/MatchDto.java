package com.ggwp.searchservice.match.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.match.domain.Participant;
import com.ggwp.searchservice.match.domain.Team;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchDto {

    @JsonProperty("info")
    private InfoDto info;

    @JsonProperty("metadata")
    private MetadataDto metadataDto;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class MetadataDto {
        @JsonProperty()
        private String matchId;
    }

    public Match toEntity() {

        Match match = Match.builder()
                .matchId(metadataDto.getMatchId())
                .platformId(info.getPlatformId())
                .queueId(info.getQueueId())
                .gameCreation(info.getGameCreation())
                .gameDuration(info.getGameDuration())
                .gameStartTimestamp(info.getGameStartTimestamp())
                .gameEndTimestamp(info.getGameEndTimestamp())
                .build();

        List<Team> teamEntities = info.getTeams().stream()
                .map(teamDto -> {
                    Team team = teamDto.toEntity();
                    team.setMatch(match); // Match와 Team 연결
                    return team;
                })
                .collect(Collectors.toList());

        List<Participant> participantEntities = info.getParticipants().stream()
                .map(participantDto -> {
                    int teamId = participantDto.getTeamId();

                    Team matchingTeam = teamEntities.stream()
                            .filter(team -> team.getTeamId() == teamId)
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Team not found for teamId: " + teamId));

                    Participant participant = participantDto.toEntity(matchingTeam);
                    matchingTeam.addParticipant(participant); // 해당 Team에 Participant 추가
                    return participant;
                })
                .collect(Collectors.toList());

        match.setTeams(teamEntities);
        return match;
    }



//    @JsonProperty("matchId")
//    private String matchId;

}
