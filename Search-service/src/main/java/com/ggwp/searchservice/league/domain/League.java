package com.ggwp.searchservice.league.domain;

import com.ggwp.searchservice.enums.GameMode;
import com.ggwp.searchservice.summoner.domain.Summoner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class League {

    @Id
    private String leagueId; // 리그 아이디 encrypted

    private String queueType; // 게임 모드

    private String tier; // 티어 (실버, 골드, 다이아)

    private String ranks; // 랭크 ( 1단계, 2단계, 3단계)

    private int leaguePoints; // 포인트

    private int wins; // 승리

    private int losses; // 패배

    // 다대일 연결 ( 리그 2 : 소환사 1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "summoner_id")
    private Summoner summoner;

}

