package com.ggwp.searchservice.match.domain;

import com.ggwp.searchservice.summoner.domain.Summoner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MatchSummoner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_summoner_pk", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "summoner_id")
    private Summoner summoner;
}
