package com.qa.account.controller;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qa.account.model.Account;
import com.qa.account.model.AccountResponse;
import com.qa.account.service.AccountService;
import com.qa.account.service.exception.AccountCreationException;
import com.qa.account.service.exception.AccountNotFoundException;

@RestController
@RequestMapping("/account-project/rest/account/json")
public class AccountController {

  @Autowired
  AccountService service;

  @GetMapping("")
  public ResponseEntity<List<Account>> getAccounts() {
    List<Account> accounts = service.getAccounts();
    if (null != accounts) {
      return new ResponseEntity<>(accounts, HttpStatus.OK);
    } else {
      throw new AccountNotFoundException("Accounts not Found");
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable String id) {
    Account account = service.getAccountsById(Integer.parseInt(id));
    if (null != account) {
      return new ResponseEntity<>(account, HttpStatus.OK);
    } else {
      throw new AccountNotFoundException(String.format("No Account Found for id %s", id));
    }
  }

  @PostMapping("")
  public ResponseEntity<AccountResponse> createAccount(@RequestBody Account acc) {
    if (acc == null) {
      throw new AccountCreationException(
          "Invalid Request Data.");
    }
    if (StringUtils.isBlank(acc.getFirstName()) || StringUtils.isBlank(acc.getSecondName())
        || StringUtils.isBlank(acc.getAccountNumber())) {

      throw new AccountCreationException(
          "Invalid Request Data. First name, second name and account number must be provided.");
    } else {

      Account account = service.createAccount(acc);
      return new ResponseEntity<>(new AccountResponse("Account successfully created with Id " + account.getId()),
          HttpStatus.CREATED);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<AccountResponse> deleteAccountById(@PathVariable String id) {
    try {
      service.deleteAccount(Integer.parseInt(id));
    } catch (AccountNotFoundException e) {
      throw new AccountNotFoundException(e.getMessage() + String.format("No Account Found for id %s", id));
    }
    return new ResponseEntity<>(new AccountResponse("Account successfully deleted"), HttpStatus.OK);
  }


}
