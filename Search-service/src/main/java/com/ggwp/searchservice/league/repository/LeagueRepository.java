package com.ggwp.searchservice.league.repository;


import com.ggwp.searchservice.league.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeagueRepository extends JpaRepository<League,Long> {

    List<League> findLeaguesBySummonerId(String summonerId);
}
