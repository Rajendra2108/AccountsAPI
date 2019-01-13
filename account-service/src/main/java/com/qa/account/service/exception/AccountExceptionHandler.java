package com.qa.account.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorDetails> handleServiceExceptions(Exception ex, WebRequest req) {
    ErrorDetails ed = new ErrorDetails(ex.getMessage(), req.getDescription(false));
    return new ResponseEntity<>(ed, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  @ExceptionHandler(AccountNotFoundException.class)
  public final ResponseEntity<ErrorDetails> handleAccountNotFoundExceptions(Exception ex, WebRequest req) {
    ErrorDetails ed = new ErrorDetails(ex.getMessage(), req.getDescription(false));
    return new ResponseEntity<>(ed, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AccountCreationException.class)
  public final ResponseEntity<ErrorDetails> handleCreationException(Exception ex, WebRequest req) {
    ErrorDetails ed = new ErrorDetails(ex.getMessage(), req.getDescription(false));
    return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);
  }
}
