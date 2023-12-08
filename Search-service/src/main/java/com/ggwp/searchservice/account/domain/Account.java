package com.ggwp.searchservice.account.domain;

import com.ggwp.searchservice.account.dto.FeignAccountDto;
import com.ggwp.searchservice.summoner.domain.Summoner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Account {

    @Id
    @Column(length = 78, unique = true, nullable = false)
    private String puuid; // Summoner의 puuid

    @Column(length = 63, nullable = false)
    private String gameName; // 롤 닉네임

    @Column(nullable = true) // - Null일 수 있다.
    private String tagLine; // 롤 태그

    @OneToOne // (계정과 소환사는 1:1 관계이다.)
    @JoinColumn(name = "summoner_id")
    private Summoner summoner;

    @Version
    private int version;

    public void updateAccount(FeignAccountDto accountDto) {
        this.gameName = accountDto.getGameName();
        this.tagLine = accountDto.getTagLine();
    }
}
