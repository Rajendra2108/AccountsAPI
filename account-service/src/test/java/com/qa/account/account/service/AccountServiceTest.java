package com.qa.account.account.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.qa.account.dao.AccountsRepository;
import com.qa.account.model.Account;
import com.qa.account.service.AccountService;
import com.qa.account.service.exception.AccountNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountServiceTest {

  @Autowired
  AccountService service;

  @MockBean
  AccountsRepository repositoryMock;

  @Before(value = "")
  public void before() {
    MockitoAnnotations.initMocks(repositoryMock);

  }

  @Test
  public void shouldReturnAllAccounts() throws Exception {

    List<Account> accs = Arrays.asList(new Account("Test", "User", "1111"), new Account("Json", "Bourn", "1112"));
    Mockito.when(repositoryMock.findAll()).thenReturn(accs);

    List<Account> accounts = service.getAccounts();
    assertThat(accounts, is(notNullValue()));
    assertThat(accounts.size(), is(2));
  }

  @Test
  public void shouldReturnAccountForGivenId() throws Exception {
    Optional<Account> acc = Optional.ofNullable(new Account("Bat", "Robin", "11113"));
    Mockito.when(repositoryMock.findById(Mockito.anyInt())).thenReturn(acc);

    Account account = service.getAccountsById(2);
    assertThat(account, is(notNullValue()));
    assertThat(account.getAccountNumber(), is(equalTo("11113")));
    assertThat(account.getSecondName(), is(equalTo("Robin")));
  }

  @Test
  public void shouldReturnNullForUnavailableUserId() throws Exception {
    Optional<Account> acc = Optional.empty();
    Mockito.when(repositoryMock.findById(Mockito.anyInt())).thenReturn(acc);

    Account account = service.getAccountsById(22);
    assertThat(account, is(nullValue()));
  }

  @Test
  public void shouldCreateAccountForGivenAccountDetails() throws Exception {
    Account acc = new Account("Super", "man", "11133");
    Mockito.when(repositoryMock.save(Mockito.any())).thenReturn(acc);
    Account account = service.createAccount(acc);
    assertThat(account, is(notNullValue()));
    assertThat(account.getAccountNumber(), is(equalTo("11133")));
    assertThat(account.getSecondName(), is(equalTo("man")));
  }

  @Test
  public void shouldDeleteAccountForGivenValidAccountId() throws Exception {

    Account acc = new Account("Super", "man", "11133");
    Mockito.when(repositoryMock.findById(5)).thenReturn(Optional.of(acc));
    doNothing().when(repositoryMock).deleteById(Mockito.anyInt());

    service.deleteAccount(5);
  }

  @Test(expected = AccountNotFoundException.class)
  public void shouldThrowExceptionDeletingUnavailableAccountId() throws Exception {

    Mockito.when(repositoryMock.findById(5)).thenReturn(Optional.empty());
    doNothing().when(repositoryMock).deleteById(Mockito.anyInt());

    service.deleteAccount(5);
  }
}
