package com.ggwp.squadservice.repository;

import com.ggwp.squadservice.domain.Squad;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SquadRepository extends JpaRepository<Squad, Long>, JpaSpecificationExecutor<Squad> {

    // Specification을 사용한 동적 쿼리
    List<Squad> findAllBySpec(Specification<Squad> spec);

}
