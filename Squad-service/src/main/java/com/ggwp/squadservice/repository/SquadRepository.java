package com.ggwp.squadservice.repository;

import com.ggwp.squadservice.domain.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface SquadRepository extends JpaRepository<Squad, Long>, JpaSpecificationExecutor<Squad> {
    @Query("SELECT s FROM Squad s ORDER BY s.createdAt DESC")
    List<Squad> findAllOrderByCreatedAtDesc();

    List<Squad> findByCreatedAtBeforeAndOutdatedFalse(Timestamp date);

    @Query("SELECT s.sId FROM Squad s WHERE s.approved = true AND s.summonerName = :summonerName AND s.tagLine = :tagLine")
    List<Long> findSIdByApprovedAndSummonerNameAndTagLine(@Param("summonerName") String summonerName, @Param("tagLine") String tagLine);

}
