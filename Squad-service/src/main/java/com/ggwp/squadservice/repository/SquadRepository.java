package com.ggwp.squadservice.repository;

import com.ggwp.squadservice.domain.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SquadRepository extends JpaRepository<Squad, Long>, JpaSpecificationExecutor<Squad> {
    @Query("SELECT s FROM Squad s ORDER BY s.createdAt DESC")
    List<Squad> findAllOrderByCreatedAtDesc();
}
