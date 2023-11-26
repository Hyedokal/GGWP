package com.ggwp.searchservice.summoner.domain;

import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.summoner.dto.ResponseGetSummonerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @OneToMany(mappedBy = "summoner")
    private List<League> leagues;

    @ManyToMany(mappedBy = "summoners")
    private List<Match> matches = new ArrayList<>();

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

    public void addMatch(Match match) {
        if (this.matches == null) {
            this.matches = new ArrayList<>();
        }
        this.matches.size(); // Lazy 때문에 해야함
        this.matches.add(match);
    }
}
