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

    // mhlee: 이부분은 제가 봤을때, 좀 문제가 있어 보입니다.
    // 1. 서비스 레이어를 중심으로 구현하고 테스트해야 하는데, 서비스에는 아무 로직이 없고, 하찮은 dto에 로직이 있다??
    // 2. 일단 전체 프로그램의 흐름을 봐야겠지만, 연관관계 필요에 대한 의구심이 듭니다.
    // 2-1. 객체 탐색(match -> team) 이런식으로 로직이 흘러가는게 있는가?
    // 2-2. 아니라면, 아래의 복잡한 로직은 연관관계가 맺어져서 매칭하느라 그런것인가?
    // 3. 내용만 보면(제가 LOL을 몰라 잘못 이해 했으룻 있습니다.)
    // 3-1. Match가 있다
    // 3-2. Match에 Team이 있다.
    // 3-3. Team에는 Participant가 있다.

    // 4. 로직을 정리해보면, 아래 3가지 메소드만 구현하면 되는것 아닐까요?
    // 4-1. Match createMatch(match생성DTO) 
    // 4-2. Team createTeam(Match match, team생성DTO) 
    // 4-3. List<Participant> createParticipant(Team team, List<participant생성DTO>)

    // 5. 아래 내용은 억지스럽게 연관관계를 맺고, 그것에 껴맞추려고 한것 같은 느낌입니다.
    // 6. 마지막으로, 제 개인적으로 MSA는 이미 도메인을 최소 단위로 잘라났기 때문에, 항상 eager로 땡겨야 합니다.
    // 7. lazy는 프로덕션 레벨에서 별로 권장되지 않을거예요. lazy라 좋을것 같지만, JPA 구조상 문제만 발생(batch 애노테이션등으로 완화가능)
    // 8. 즉, 여러개의 도메인(Match, Participant, Team) 같이 땡겨야 하면, 제 개인적으로는
    // 9. QueryDSL로 join처리해보리고, Triple<Match, Participant, Team> 이런식으로 묶어서 리턴합니다.
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
