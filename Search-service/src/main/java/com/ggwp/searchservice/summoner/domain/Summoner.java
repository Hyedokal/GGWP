package com.ggwp.searchservice.summoner.domain;

import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.summoner.dto.ResponseGetSummonerDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Long summonerLevel; // 소환사 레벨

    // 리그 연결 일대다 ( 소환사 1 : 리그 2)
    @OneToMany(mappedBy = "summoner")
    private List<League> leagues;

//    // 매치 연결 일대다 ( 소환사 1 : 매치 10)
//    @OneToMany(mappedBy = "summoner")
//    private List<Match> matches;

    // 소환사 Entity를 -> DTO로 변경
    public ResponseGetSummonerDto toDto(Summoner summoner){
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
}
