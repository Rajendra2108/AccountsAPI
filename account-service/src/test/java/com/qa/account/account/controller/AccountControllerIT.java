package com.qa.account.account.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.account.model.Account;
import com.qa.account.service.AccountService;
import com.qa.account.service.exception.AccountCreationException;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class AccountControllerIT {

  @Autowired
  AccountService service;

  @Autowired
  MockMvc mockMvc;

  private static final String ACCOUNTS_URL = "/account-project/rest/account/json";

  @Before(value = "")
  public void setUp() {

  }

  @Test
  public void shouldReturnErrorGivenIncorrectURL() throws Exception {
    mockMvc.perform(get("/rest/account/json")).andDo(print()).andExpect(status().is4xxClientError());
  }

  @Test
  public void shouldReturnErrorGivenIncorrectHttpMethod() throws Exception {
    mockMvc.perform(post("/account-project/rest/account/json")).andDo(print()).andExpect(status().is4xxClientError());
  }

  @Test
  public void shouldReturnSuccessResponseGivenCorrectURL() throws Exception {
    mockMvc.perform(get(ACCOUNTS_URL)).andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void shouldReturnOneAccountGivenValidAccountId() throws Exception {
    mockMvc.perform(get(ACCOUNTS_URL + "/1")).andDo(print()).andExpect(status().isOk()).andExpect(
        content().json("{\"id\":1,\"firstName\":\"Jane\",\"secondName\":\"Bike\",\"accountNumber\":\"1111\"}"));
  }

  @Test
  public void shouldReturnNoAccountFoundGivenUnavailableAccountId() throws Exception {
    mockMvc.perform(get(ACCOUNTS_URL + "/31")).andDo(print()).andExpect(status().isNotFound())
        .andExpect(content().json("{\"message\":\"No Account Found for id 31\"}"));
  }

  @Test(expected = AccountCreationException.class)
  public void shouldThrowExceptionGivenInvalidAccountRequestBody() throws Exception {
    Account account = new Account("", "Crow", "12345");
    mockMvc
        .perform(post(ACCOUNTS_URL).content(objectToJson(account)).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().json(
            "{\"message\":\"Invalid Request Data. First name, second name and account number must be provided.\"}"));;
  }

  @Test
  public void shouldThrowExceptionGivenInvalidAccountRequest() throws Exception {
    Account account = new Account("", "", "");
    mockMvc
        .perform(post(ACCOUNTS_URL).content(objectToJson(account)).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().json(
            "{\"message\":\"Invalid Request Data. First name, second name and account number must be provided.\"}"));;
  }

  @Test
  public void shouldCreateAccountGivenAccountRequest() throws Exception {
    Account account = new Account("George", "Crow", "12345");
    mockMvc
        .perform(post(ACCOUNTS_URL).content(objectToJson(account)).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isCreated())
        .andExpect(content().json("{\"message\":\"Account successfully created\"}"));;
  }

  @Test
  public void shouldDeleteAccountGivenValidAccountId() throws Exception {
    mockMvc.perform(delete(ACCOUNTS_URL + "/3")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().json("{\"message\":\"Account successfully deleted\"}"));
  }

  @Test
  public void shouldReturnAccountNotFoundGivenInValidAccountIdForDeletion() throws Exception {
    mockMvc.perform(delete(ACCOUNTS_URL + "/21")).andDo(print()).andExpect(status().isNotFound())
        .andExpect(content().json("{\"message\":\"Failed to delete account. No Account Found for id 21\"}"));
  }

  private static String objectToJson(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }

}
