package com.ggwp.searchservice.summoner.repository;


import com.ggwp.searchservice.summoner.domain.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SummonerRepository extends JpaRepository<Summoner, Long> {
    boolean existsByPuuid(String puuid);

    Summoner findSummonerByPuuid(String puuid);

    Optional<Summoner> findByPuuid(String puuid);
}
