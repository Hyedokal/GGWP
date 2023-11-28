package com.ggwp.searchservice.match.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "matches")
public class Match {

    @Id
    @Column(name = "match_id")
    private String matchId;
    @Column(name = "platform_id")
    private String platformId; // KR

    @Column(name = "queue_id")
    private int queueId; // 게임 모드

    @Column(name = "game_creation")
    private long gameCreation; // 게임 생성 시각

    @Column(name = "game_duration")
    private long gameDuration; // 게임 지속 시각

    @Column(name = "game_end_timestamp")
    private long gameEndTimestamp; // 게임 종료 시각

    @Column(name = "game_start_timestamp")
    private long gameStartTimestamp; // 게임 시작 시각

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MatchSummoner> matchSummoners = new ArrayList<>();

    public void addTeams(List<Team> teams) {
        this.teams.addAll(teams);
        for (Team team : teams) {
            team.setMatch(this); // Team과 Match 연관관계 설정
        }
    }

    public void addMatchSummoners(List<MatchSummoner> matchSummoners) {
        Hibernate.initialize(this.matchSummoners);

        if (this.matchSummoners == null) {
            this.matchSummoners = new ArrayList<>();
        }

        this.matchSummoners.addAll(matchSummoners);
    }
}
