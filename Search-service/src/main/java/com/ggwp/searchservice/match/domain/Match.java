package com.ggwp.searchservice.match.domain;

import com.ggwp.searchservice.common.enums.GameMode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "matches")
@DynamicInsert
@DynamicUpdate
public class Match {

    @Id
    @Column(name = "match_id", nullable = false, unique = true)
    private String matchId;
    @Column(name = "platform_id", nullable = false)
    private String platformId; // KR

//    @Column(name = "queue_id", nullable = false)
//    private int queueId; // 게임 모드

    @Column(name = "queue_id", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameMode queueId; // 게임 모드

    @Column(name = "game_creation", nullable = false)
    private long gameCreation; // 게임 생성 시각

    @Column(name = "game_duration", nullable = false)
    private long gameDuration; // 게임 지속 시각

    @Column(name = "game_start_timestamp", nullable = false)
    private long gameStartTimestamp; // 게임 시작 시각

    @Column(name = "game_end_timestamp", nullable = false)
    private long gameEndTimestamp; // 게임 종료 시각

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Participant> participants;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Team> teams;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MatchSummoner> matchSummoners;

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

    public void addParticipants(List<Participant> participantList) {
        if (this.participants == null) {
            this.participants = new ArrayList<>();
        }
        this.participants.addAll(participantList);
    }
}
