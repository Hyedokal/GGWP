package com.ggwp.searchservice.league.domain;

import com.ggwp.searchservice.league.dto.CreateLeagueDto;
import com.ggwp.searchservice.league.dto.ResponseLeagueDto;
import com.ggwp.searchservice.summoner.domain.Summoner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "league_pk", nullable = false, unique = true)
    private Long id;
    @Column(nullable = false)
    private String leagueId; // 리그 아이디 encrypted
    @Column(nullable = false)
    private String queueType; // 게임 모드
    @Column(nullable = false)
    private String tier; // 티어 (실버, 골드, 다이아)
    @Column(nullable = false)
    private String ranks; // 랭크 ( 1단계, 2단계, 3단계)
    @Column(nullable = false)
    private int leaguePoints; // 포인트
    @Column(nullable = false)
    private int wins; // 승리
    @Column(nullable = false)
    private int losses; // 패배

    @Version
    private int version;

    // 다대일 연결 ( 리그 2 : 소환사 1)
    @ManyToOne
    @JoinColumn(name = "summoner_id")
    private Summoner summoner;

    public ResponseLeagueDto toDto(League league) {
        return ResponseLeagueDto.builder()
                .leagueId(league.getLeagueId())
                .queueType(league.getQueueType())
                .tier(league.getTier())
                .rank(league.getRanks())
                .leaguePoints(league.getLeaguePoints())
                .wins(league.getWins())
                .losses(league.getLosses())
                .build();
    }

    public void updateLeague(CreateLeagueDto createLeagueDto) {
        this.leagueId = createLeagueDto.getLeagueId();
        this.leaguePoints = createLeagueDto.getLeaguePoints();
        this.wins = createLeagueDto.getWins();
        this.losses = createLeagueDto.getLosses();
        this.ranks = createLeagueDto.getRank();
        this.tier = createLeagueDto.getTier();
    }
}

