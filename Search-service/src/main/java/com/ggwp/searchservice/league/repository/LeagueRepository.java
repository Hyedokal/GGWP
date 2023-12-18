package com.ggwp.searchservice.league.repository;


import com.ggwp.searchservice.league.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {

    Optional<List<League>> findLeaguesBySummonerId(String summonerId);
    boolean existsLeaguesBySummonerId(String summonerId);
}
