package com.ggwp.squadservice.repository;

import com.ggwp.squadservice.domain.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SquadRepository extends JpaRepository<Squad, Long>, JpaSpecificationExecutor<Squad> {

}
