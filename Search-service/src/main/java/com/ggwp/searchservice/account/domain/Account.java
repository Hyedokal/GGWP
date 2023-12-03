package com.ggwp.searchservice.account.domain;

import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.summoner.domain.Summoner;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Account {

    @Id
    @Column(length = 78, unique = true, nullable = false)
    private String puuid; // Summoner의 puuid

    @Column(length = 63, nullable = false)
    private String gameName; // 롤 닉네임

    @Column(nullable = true) // - Null일 수 있다.
    private String tagLine; // 롤 태그

    @OneToOne // ( 계정과 소환사는 1:1 관계이다.)
    @JoinColumn(name = "account")
    private Summoner summoner;

    @Builder
    public Account(String puuid, String gameName, String tagLine, Summoner summoner) {
        Account.builder()
                .puuid(puuid)
                .gameName(gameName)
                .tagLine(tagLine)
                .summoner(summoner)
                .build();
    }

    public ResponseAccountDto toDto() { // Dto로 변환
        return ResponseAccountDto.builder()
                .puuid(this.puuid)
                .gameName(this.gameName)
                .tagLine(this.tagLine)
                .build();
    }
}
