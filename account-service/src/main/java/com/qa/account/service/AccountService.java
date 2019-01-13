package com.qa.account.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.qa.account.dao.AccountsRepository;
import com.qa.account.model.Account;
import com.qa.account.service.exception.AccountNotFoundException;

@Component
public class AccountService {

  @Autowired
  AccountsRepository repo;

  public List<Account> getAccounts() {
    return repo.findAll();
  }

  public Account getAccountsById(int id) {
    Optional<Account> accountWrapper = repo.findById(id);
    if (accountWrapper.isPresent())
      return accountWrapper.get();
    return null;
  }

  public Account createAccount(Account account) {
    return repo.save(account);
  }

  public void deleteAccount(int id) {
    Account acc = getAccountsById(id);
    if (null != acc) {
      repo.deleteById(id);
    } else {
      throw new AccountNotFoundException("Failed to delete account. ");
    }
  }
  
}
