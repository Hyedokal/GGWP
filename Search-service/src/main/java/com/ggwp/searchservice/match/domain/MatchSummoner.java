package com.ggwp.searchservice.match.domain;

import com.ggwp.searchservice.summoner.domain.Summoner;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;


@Entity
@Getter
public class MatchSummoner {

    @Id
    private String matchId;

    @Id
    private String summonerId;

    @ManyToOne
    @JoinColumn(name = "match_id", insertable = false, updatable = false)
    private Match match;

    @ManyToOne
    @JoinColumn(name = "summoner_id", insertable = false, updatable = false)
    private Summoner summoner;

}