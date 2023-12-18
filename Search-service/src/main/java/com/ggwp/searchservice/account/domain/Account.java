package com.ggwp.searchservice.account.domain;

import com.ggwp.searchservice.account.dto.CreateAccountDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
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

    @Version
    private int version;

    public void updateAccount(CreateAccountDto accountDto) {
        this.gameName = accountDto.getGameName();
        this.tagLine = accountDto.getTagLine();
    }
}
