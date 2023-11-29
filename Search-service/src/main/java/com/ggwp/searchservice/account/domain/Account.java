package com.ggwp.searchservice.account.domain;

import com.ggwp.searchservice.summoner.domain.Summoner;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    private String puuid;

    private String gameName;

    private String tagLine;

    @OneToOne
    @JoinColumn(name = "account")
    private Summoner summoner;
}
