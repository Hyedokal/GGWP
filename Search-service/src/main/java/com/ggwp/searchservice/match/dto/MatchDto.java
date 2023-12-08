package com.ggwp.searchservice.match.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

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
    public static class MetadataDto {
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
        private int queueId;

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

    }
}
