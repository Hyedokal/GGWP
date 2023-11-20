package com.ggwp.searchservice.summoner.repository;


import com.ggwp.searchservice.summoner.domain.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummonerRepository extends JpaRepository<Summoner,Long> {

    // 닉네임으로 Summoner 찾기
    Summoner findSummonerByName(String name);

    Summoner findSummonerById(String id);
}
