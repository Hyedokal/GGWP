package com.ggwp.searchservice.match.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.match.domain.Participant;
import com.ggwp.searchservice.match.domain.Team;
import com.ggwp.searchservice.summoner.dto.RequestSummonerDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static MatchDto toDto(Match match) {

        MetadataDto metadataDto = MetadataDto.builder()
                .matchId(match.getMatchId())
                .build();

        List<TeamDto> teamDtoList = new ArrayList<>();
        List<ParticipantDto> participantDtoList = new ArrayList<>();

        List<Team> teamList = match.getTeams();
        for (Team team : teamList) {
            TeamDto teamDto = TeamDto.builder()
                    .teamId(team.getTeamId())
                    .win(team.isWin())
                    .build();
            teamDtoList.add(teamDto);

            List<Participant> participantList = team.getParticipants();
            for (Participant participant : participantList) {

                PerkStyleDto primaryPerkStyleDto = PerkStyleDto.builder()
                        .description("Primary Style Description")
                        .style(participant.getPrimaryStyle())
                        .build();

                PerkStyleDto subPerkStyleDto = PerkStyleDto.builder()
                        .description("Sub Style Description")
                        .style(participant.getSubStyle())
                        .build();

                PerksDto perksDto = PerksDto.builder()
                        .styles(Arrays.asList(primaryPerkStyleDto, subPerkStyleDto))
                        .build();

                ParticipantDto participantDto = ParticipantDto.builder()
                        .participantId(participant.getParticipantId())
                        .summonerId(participant.getSummonerId())
                        .profileIcon(participant.getProfileIcon())
                        .puuid(participant.getPuuid())
                        .summmonerLevel(participant.getChampLevel())
                        .summonerName(participant.getSummonerName())
                        .kills(participant.getKills())
                        .assists(participant.getAssists())
                        .deaths(participant.getDeaths())
                        .champExperience(participant.getChampExperience())
                        .champLevel(participant.getChampLevel())
                        .championId(participant.getChampionId())
                        .championName(participant.getChampionName())
                        .item0(participant.getItem0())
                        .item1(participant.getItem1())
                        .item2(participant.getItem2())
                        .item3(participant.getItem3())
                        .item4(participant.getItem4())
                        .item5(participant.getItem5())
                        .item6(participant.getItem6())
                        .summoner1Id(participant.getSummoner1Id())
                        .summoner2Id(participant.getSummoner2Id())
                        .neutralMinionsKilled(participant.getNeutralMinionsKilled())
                        .totalMinionsKilled(participant.getTotalMinionsKilled())
                        .totalDamageDealtToChampions(participant.getTotalDamageDealtToChampions())
                        .totalDamageTaken(participant.getTotalDamageTaken())
                        .teamId(participant.getTeam().getTeamId())
                        .perks(perksDto)
                        .teamPosition(participant.getTeamPosition())
                        .build();

                participantDtoList.add(participantDto);
            }
        }

        InfoDto infoDto = InfoDto.builder()
                .gameCreation(match.getGameCreation())
                .gameDuration(match.getGameDuration())
                .gameStartTimestamp(match.getGameStartTimestamp())
                .gameEndTimestamp(match.getGameEndTimestamp())
                .platformId(match.getPlatformId())
                .queueId(match.getQueueId())
                .teams(teamDtoList)
                .participants(participantDtoList)
                .build();

        return MatchDto.builder()
                .info(infoDto)
                .metadataDto(metadataDto)
                .build();
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
                .teams(new ArrayList<>())  // teams는 null 이다.
                .build();

        List<Team> teamEntities = info.getTeams().stream()
                .map(teamDto -> {
                    Team team = teamDto.toEntity();

                    // 이 부분에서 Team에 Participant를 추가
                    List<Participant> participantEntities = info.getParticipants().stream()
                            .filter(participantDto -> participantDto.getTeamId() == team.getTeamId())
                            .map(participantDto -> {
                                Participant participant = participantDto.toEntity(team);
                                team.addParticipant(participant);
                                return participant;
                            })
                            .collect(Collectors.toList());
                    team.setMatch(match);
                    return team;
                })
                .collect(Collectors.toList());

        match.addTeams(teamEntities);

        return match;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class MetadataDto {
        @JsonProperty("matchId")
        private String matchId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InfoDto {
        @JsonProperty("participants")
        List<ParticipantDto> participants;
        @JsonProperty("gameCreation")
        private Long gameCreation;
        @JsonProperty("gameDuration")
        private long gameDuration; // 게임 지속 시각
        @JsonProperty("gameEndTimestamp")
        private long gameEndTimestamp; // 게임 종료 시각
        @JsonProperty("gameStartTimestamp")
        private long gameStartTimestamp; // 게임 시작 시각
        @JsonProperty("platformId")
        private String platformId;

        @JsonProperty("queueId")
        private Integer queueId;

        @JsonProperty("teams")
        private List<TeamDto> teams;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ParticipantDto {

        @JsonProperty("participantId")
        private int participantId;

        //////////////////////// 소환사 ////////////////////////////
        @JsonProperty("summonerId")
        private String summonerId;
        @JsonProperty("profileIcon")
        private int profileIcon;

        @JsonProperty("puuid")
        private String puuid;

        @JsonProperty("summonerLevel")
        private int summmonerLevel;

        @JsonProperty("summonerName")
        private String summonerName;

        //// Account////////////
        @JsonProperty("riotIdTagline")
        private String riotIdTagline;
        ////////////////////////////////////////////////////////////

        ///////////////////////kda /////////////////////////////////
        @JsonProperty("kills")
        private int kills;
        @JsonProperty("assists")
        private int assists;
        @JsonProperty("deaths")
        private int deaths;
        ////////////////////////////////////////////////////////////
        /////////////////////////// 챔피언 /////////////////////////
        @JsonProperty("champExperience")
        private int champExperience;  // 챔피언 숙련도
        @JsonProperty("champLevel")
        private int champLevel; // 챔피언 레벨
        @JsonProperty("championId")
        private int championId; // 챔피언 아이디
        @JsonProperty("championName")
        private String championName; // 챔피언 이름
        ///////////////////////////////////////////////////////////
        //////////////////// 아이템 및 룬///////////////////////////
        @JsonProperty("item0")
        private int item0;
        @JsonProperty("item1")
        private int item1; // 아이템 번호1
        @JsonProperty("item2")
        private int item2; // 아이템 번호2
        @JsonProperty("item3")
        private int item3; // 아이템 번호3
        @JsonProperty("item4")
        private int item4; // 아이템 번호4
        @JsonProperty("item5")
        private int item5; // 아이템 번호5
        @JsonProperty("item6")
        private int item6; // 아이템 번호6 (와드 부분)
        @JsonProperty("summoner1Id")
        private int summoner1Id;

        @JsonProperty("summoner2Id")
        private int summoner2Id;

        @JsonProperty("perks")
        private PerksDto perks;

        ////////////////////////////////////////////////////////
        ///////////////////////////// CS ///////////////////////
        @JsonProperty("neutralMinionsKilled")
        private int neutralMinionsKilled;

        @JsonProperty("totalMinionsKilled")
        private int totalMinionsKilled; // 미니언 cs
        ////////////////////////////////////////////////////////
        /////////////////////// 딜량 ////////////////////////////
        @JsonProperty("totalDamageDealtToChampions")
        private int totalDamageDealtToChampions; // 총 딜량
        @JsonProperty("totalDamageTaken")
        private int totalDamageTaken; // 총 탱량
        /////////////////////////////////////////////////////////
        @JsonProperty("teamId")
        private int teamId;

        @JsonProperty("teamPosition")
        private String teamPosition;
        /////////////////////////////////////////////////////////

        public Participant toEntity(Team team) {
            return Participant.builder()
                    .participantId(this.participantId)
                    .summonerId(this.summonerId)
                    .profileIcon(this.profileIcon)
                    .puuid(this.puuid)
                    .summonerLevel(this.summmonerLevel)
                    .summonerName(this.summonerName)
                    .riotIdTagline(this.riotIdTagline)
                    .kills(this.kills)
                    .assists(this.assists)
                    .deaths(this.deaths)
                    .champExperience(this.champExperience)
                    .champLevel(this.champLevel)
                    .championId(this.championId)
                    .championName(this.championName)
                    .item0(this.item0)
                    .item1(this.item1)
                    .item2(this.item2)
                    .item3(this.item3)
                    .item4(this.item4)
                    .item5(this.item5)
                    .item6(this.item6)
                    .summoner1Id(this.summoner1Id)
                    .summoner2Id(this.summoner2Id)
                    .neutralMinionsKilled(this.neutralMinionsKilled)
                    .totalMinionsKilled(this.totalMinionsKilled)
                    .totalDamageDealtToChampions(this.totalDamageDealtToChampions)
                    .totalDamageTaken(this.totalDamageTaken)
                    .primaryStyle(this.perks.getStyles().get(0).style)
                    .subStyle(this.perks.getStyles().get(1).style)
                    .teamPosition(this.teamPosition)
                    .team(team)
                    .build();
        }

        public RequestSummonerDto createSummonerDto() {
            return RequestSummonerDto.builder()
                    .id(this.summonerId)
                    .profileIconId(this.profileIcon)
                    .name(this.summonerName)
                    .summonerLevel(this.summmonerLevel)
                    .puuid(this.puuid)
                    .build();
        }

        public ResponseAccountDto createAccountDto() {
            return ResponseAccountDto.builder()
                    .puuid(this.puuid)
                    .gameName(this.summonerName)
                    .tagLine(this.riotIdTagline)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PerksDto {

        @JsonProperty("styles")
        List<PerkStyleDto> styles;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PerkStyleDto {
        @JsonProperty("description")
        private String description;

        @JsonProperty("style")
        private int style;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeamDto {
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
}
