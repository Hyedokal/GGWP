package com.ggwp.searchservice.summoner.domain;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.match.domain.MatchSummoner;
import com.ggwp.searchservice.summoner.dto.CreateSummonerDto;
import com.ggwp.searchservice.summoner.dto.ResponseSummonerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Summoner {

    @Id
    @Column(name = "summoner_id", length = 63, unique = true) // varchar(63)
    private String id; // summoner_id encrypted

    @Column(nullable = false)
    private int profileIconId; // 프로필 아이콘 번호

    @Column(length = 78, unique = true, nullable = false)
    private String puuid; // summoner의 puuid

    @Column(length = 63, nullable = false)
    private String name; // 롤 닉네임

    @Column(nullable = false)
    private Long revisionDate; // 전적 업데이트 날짜

    @Column(nullable = false)
    private int summonerLevel; // 소환사 레벨

//    @Version
//    private int version;

    // 리그 연결 일대다 ( 소환사 1 : 리그 2)
    @OneToMany(mappedBy = "summoner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<League> leagues;
    // Summoner(OneToMany) <-> MatchSummoner(ManyToOne) <-> Match(OneToMany)  // ManyToMany 연관관계
    @OneToMany(mappedBy = "summoner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MatchSummoner> matchSummoners;
    // 계정 연결 일대일 (소환사 1 : 계정 1)
    @OneToOne(mappedBy = "summoner", cascade = CascadeType.ALL)
    private Account account;

    // 소환사 Entity를 -> DTO로 변경
    public ResponseSummonerDto toDto(Summoner summoner) {
        return ResponseSummonerDto.builder()
                .id(summoner.getId())
                .profileIconId(summoner.getProfileIconId())
                .puuid(summoner.getPuuid())
                .name(summoner.getName())
                .revisionDate(summoner.getRevisionDate())
                .summonerLevel(summoner.getSummonerLevel())
                .build();
    }

    // 연관관계 설정 (Account)
    public void addAccount(Account account) {
        this.account = account;
    }

    // 연관관계 설정 (League)
    public void addLeagues(List<League> leagueList) {
        Hibernate.initialize(this.leagues);
        if (this.leagues == null) {
            this.leagues = new ArrayList<>();
        }
        this.leagues.addAll(leagueList);
    }

    public void updateSummoner(CreateSummonerDto createSummonerDto, String gameName) {
        this.name = gameName;
        this.profileIconId = createSummonerDto.getProfileIconId();
        this.puuid = createSummonerDto.getPuuid();
        this.revisionDate = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli(); // 업데이트
        this.summonerLevel = createSummonerDto.getSummonerLevel();
    }
}
