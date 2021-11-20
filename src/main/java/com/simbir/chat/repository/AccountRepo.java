package com.simbir.chat.repository;

import com.simbir.chat.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findByFirstName(String firstName);
    Optional<Account> findByEmail(String email);
}
