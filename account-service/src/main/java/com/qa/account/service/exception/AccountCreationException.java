package com.qa.account.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountCreationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AccountCreationException(String msg) {
    super(msg);
  }

}
