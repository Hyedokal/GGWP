package com.ggwp.searchservice.match.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_pk", nullable = false, unique = true)
    private Long participantPk;

    @Column(name = "participant_id", nullable = false)
    private int participantId;

    ///////////////// summoner ////////////////////////////////////
    @Column(name = "summoner_id", nullable = false)
    private String summonerId;

    @Column(nullable = false)
    private int profileIcon;

    @Column(name = "puuid", nullable = false)
    private String puuid;

    @Column(name = "summoner_name", nullable = false)
    private String summonerName;

    @Column(name = "summoner_level", nullable = false)
    private int summonerLevel;

    @Column(name = "riotIdTagline", nullable = true) // null 가능
    private String riotIdTagline;

    ////////////////////////////////////////////////////////////
    @Column(nullable = false)
    private int champExperience; // 챔피언 숙련도
    @Column(nullable = false)
    private int champLevel; // 챔피언 레벨
    @Column(nullable = false)
    private int championId; // 챔피언 아이디
    @Column(nullable = false)
    private String championName; // 챔피언 이름
    @Column(nullable = false)
    private int kills;
    @Column(nullable = false)
    private int deaths;
    @Column(nullable = false)
    private int assists;
    @Column(nullable = false)
    private int summoner1Id; // 스펠 번호1 (점화)
    @Column(nullable = false)
    private int summoner2Id; // 스펠 번호2 (점멸)
    @Column(nullable = false)
    private int item0; // 아이템 번호0
    @Column(nullable = false)
    private int item1; // 아이템 번호1
    @Column(nullable = false)
    private int item2; // 아이템 번호2
    @Column(nullable = false)
    private int item3; // 아이템 번호3
    @Column(nullable = false)
    private int item4; // 아이템 번호4
    @Column(nullable = false)
    private int item5; // 아이템 번호5
    @Column(nullable = false)
    private int item6; // 아이템 번호6 (와드 부분)
    @Column(nullable = false)
    private int neutralMinionsKilled; // 모든 나머지 cs
    @Column(nullable = false)
    private int totalMinionsKilled; // 미니언 cs
    @Column(nullable = false)
    private int totalDamageDealtToChampions; // 총 딜량
    @Column(nullable = false)
    private int totalDamageTaken; // 총 탱량
    @Column(nullable = false)
    private String teamPosition;

    @Column(name = "primary_style", nullable = false)
    private int primaryStyle;

    @Column(name = "sub_style", nullable = false)
    private int subStyle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_pk")
    private Team team;

}
