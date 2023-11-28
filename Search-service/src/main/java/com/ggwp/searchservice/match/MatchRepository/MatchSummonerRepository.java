package com.ggwp.searchservice.match.MatchRepository;

import com.ggwp.searchservice.match.domain.MatchSummoner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchSummonerRepository extends JpaRepository<MatchSummoner, Long> {

    List<MatchSummoner> findBySummonerId(String summonerId);
    
}