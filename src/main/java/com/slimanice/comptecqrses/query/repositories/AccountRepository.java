package com.slimanice.comptecqrses.query.repositories;

import com.slimanice.comptecqrses.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

}
