package com.qa.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table
public class Account {

  public Account() {}

  public Account(String firstName, String secondName, String accountNumber) {
    super();
    this.firstName = firstName;
    this.secondName = secondName;
    this.accountNumber = accountNumber;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotEmpty(message = "First name can not be null")
  @Column(name = "first_name")
  private String firstName;

  @NotEmpty(message = "Second name can not be null")
  @Column(name = "second_name")
  private String secondName;

  @Valid
  @NotEmpty(message = "Acount number can not be null")
  @Column(name = "account_number")
  private String accountNumber;

}
