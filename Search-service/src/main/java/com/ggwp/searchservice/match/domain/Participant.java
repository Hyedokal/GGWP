package com.ggwp.searchservice.match.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Participant {

    @Id
    @Column(name = "participant_id")
    private int participantId; // participant의 PK

    ///////////////// summoner ////////////////////////////////////
    @Column(name = "summoner_id")
    private String summonerId;

    private int profileIcon;

    @Column(name = "puuid")
    private String puuid;

    @Column(name = "summoner_name")
    private String summonerName;

    @Column(name = "summoner_level")
    private Long summonerLevel;
    ////////////////////////////////////////////////////////////

    private int champExperience; // 챔피언 숙련도
    private int champLevel; // 챔피언 레벨
    private int championId; // 챔피언 아이디
    private String championName; // 챔피언 이름

    private int kills;
    private int deaths;
    private int assists;

    private int summoner1Id; // 스펠 번호1 (점화)
    private int summoner2Id; // 스펠 번호2 (점멸)

    private int item0; // 아이템 번호0
    private int item1; // 아이템 번호1
    private int item2; // 아이템 번호2
    private int item3; // 아이템 번호3
    private int item4; // 아이템 번호4
    private int item5; // 아이템 번호5
    private int item6; // 아이템 번호6 (와드 부분)

    private int neutralMinionsKilled; // 모든 나머지 cs
    private int totalMinionsKilled; // 미니언 cs

    private int totalDamageDealtToChampions; // 총 딜량
    private int totalDamageTaken; // 총 탱량

    private String teamPosition;

    @Column(name = "primary_style")
    private int primaryStyle;

    @Column(name = "sub_style")
    private int subStyle;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

}
