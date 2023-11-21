package com.ggwp.searchservice.match.MatchRepository;

import com.ggwp.searchservice.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match,Long> {
}
