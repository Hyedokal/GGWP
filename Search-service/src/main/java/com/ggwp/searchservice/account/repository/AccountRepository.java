package com.ggwp.searchservice.account.repository;

import com.ggwp.searchservice.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByGameNameAndTagLine(String gameName, String tagLine);
}
