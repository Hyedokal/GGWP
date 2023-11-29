package com.ggwp.searchservice.account.repository;

import com.ggwp.searchservice.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByGameNameAndTagLine(String gameName, String tagLine);
}
