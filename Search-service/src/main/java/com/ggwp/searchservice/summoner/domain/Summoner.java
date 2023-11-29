package com.ggwp.searchservice.summoner.domain;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.match.domain.MatchSummoner;
import com.ggwp.searchservice.summoner.dto.ResponseGetSummonerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Summoner {

    @Id
    @Column(name = "summoner_id")
    private String id; // summoner_id encrypted

    private int profileIconId; // 프로필 아이콘 번호

//    private String accountId; // account encrypted 필요없어 보임

    private String puuid; // summoner의 puuid

    private String name; // 롤 닉네임

    private Long revisionDate; // 전적 업데이트 날짜

    private int summonerLevel; // 소환사 레벨

    // 리그 연결 일대다 ( 소환사 1 : 리그 2)
    @OneToMany(mappedBy = "summoner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<League> leagues;

    @OneToMany(mappedBy = "summoner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MatchSummoner> matchSummoners = new ArrayList<>();

    @OneToOne(mappedBy = "summoner", cascade = CascadeType.ALL)
    private Account account;

    // 소환사 Entity를 -> DTO로 변경
    public ResponseGetSummonerDto toDto(Summoner summoner) {
        return ResponseGetSummonerDto.builder()
                .id(summoner.getId())
                .profileIconId(summoner.getProfileIconId())
//                .accountId(summoner.getAccountId())
                .puuid(summoner.getPuuid())
                .name(summoner.getName())
                .revisionDate(summoner.getRevisionDate())
                .summonerLevel(summoner.getSummonerLevel())
                .build();
    }

    public void addMatchSummoner(MatchSummoner matchSummoner) {
        // Hibernate.initialize를 통해 matchSummoners 필드 초기화
        Hibernate.initialize(this.matchSummoners);

        // this.matchSummoners가 null이면 새로운 ArrayList를 생성하여 할당
        if (this.matchSummoners == null) {
            this.matchSummoners = new ArrayList<>();
        }

        // this.matchSummoners에 새로운 MatchSummoner 추가
        this.matchSummoners.add(matchSummoner);

        // 이미 값이 담겨있다고 가정하고, 따로 연관관계 설정이 필요하지 않음
    }

    public void addAccount(Account account) {
        this.account = account;
    }

    public void addLeagues(List<League> leagueList) {
        Hibernate.initialize(this.leagues);
        if (this.leagues == null) {
            this.leagues = new ArrayList<>();
        }
        this.leagues.addAll(leagueList);
    }
}
