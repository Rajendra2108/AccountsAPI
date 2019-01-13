package com.qa.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.qa.account.model.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {

}
