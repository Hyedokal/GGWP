package com.ggwp.searchservice.match.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ggwp.searchservice.match.domain.Participant;
import com.ggwp.searchservice.match.domain.Team;
import com.ggwp.searchservice.summoner.dto.RequestCreateSummonerDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantDto {
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
    private Long summmonerLevel;

    @JsonProperty("summonerName")
    private String summonerName;

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
    public Participant toEntity(Team team) {
        return Participant.builder()
                .participantId(this.participantId)
                .summonerId(this.summonerId)
                .profileIcon(this.profileIcon)
                .puuid(this.puuid)
                .summonerLevel(this.summmonerLevel)
                .summonerName(this.summonerName)
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
                .primaryStyle(this.perks.getStyles().get(0).getStyle())
                .subStyle(this.perks.getStyles().get(1).getStyle())
                .teamPosition(this.teamPosition)
                .team(team)
                .build();
    }
}
