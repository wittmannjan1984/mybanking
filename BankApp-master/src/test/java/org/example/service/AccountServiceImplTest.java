package org.example.service;

import org.example.entity.Account;
import org.example.entity.Client;
import org.example.entity.Transaction;
import org.example.exception.AccountExistsException;
import org.example.exception.AccountNotFoundException;
import org.example.model.dto.AccountBalanceInfoDto;
import org.example.model.enums.AccountStatus;
import org.example.model.enums.AccountType;
import org.example.model.enums.CurrencyCode;
import org.example.model.enums.TransactionType;
import org.example.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository repository;

    @Mock
    private ClientServiceImpl clientService;

    @Mock
    private GenerateIban generateIban;

    @InjectMocks
    private AccountServiceImpl accountService;

    private List<Account> accounts;

    @BeforeEach
    void init() {
        accounts = Arrays.asList(
                new Account("DE12345678456783454499874558", "Test account", BigDecimal.valueOf(1000), CurrencyCode.CHF),
                new Account("DE12345678456783454499874558", "Test account", BigDecimal.valueOf(1000), CurrencyCode.CHF));
    }

    @Test
    void getAll() {
        Mockito.when(repository.findAll()).thenReturn(accounts);
        assertEquals(2, accountService.getAll().size());
    }

    @Test
    void getByIbanWhenAccountExists() {
        String iban = accounts.get(0).getIban();
        Mockito.when(repository.findById(iban)).thenReturn(Optional.ofNullable(accounts.get(0)));
        assertEquals("DE12345678456783456699433444", accountService.getByIban(iban).getIban());
    }

    @Test
    void getByIbanWhenAccountNotExists() {
        Mockito.when(repository.findById("DE12345678456783456699433444")).thenReturn(Optional.ofNullable(null));
        assertThrows(AccountNotFoundException.class, () -> accountService.getByIban("DE12345678456783456699433444"));
    }

    @Test
    void createAccountWhenAccountExists() {
        Client client = new Client(1L, "Günther", "Schmidt", "client1@mybanking.com", "Berlin Germany", "+4917657604588");
        Mockito.when(clientService.getCurrent()).thenReturn(client);
        Account accountForSave = new Account(null, "Test account", BigDecimal.valueOf(1000), CurrencyCode.CHF);
        String iban = "DE12345678456783456699433444";
        Mockito.when(generateIban.generateAccountNumber()).thenReturn(iban);

        Account accountFromDatabase = new Account(iban, "Test account", BigDecimal.valueOf(1000), CurrencyCode.CHF);
        Mockito.when(repository.findById(iban)).thenReturn(Optional.of(accountFromDatabase));

        assertThrows(AccountExistsException.class, () -> accountService.create(accountForSave));
    }

    @Test
    void createAccountWhenAccountNotExists() {
        Client client = new Client(1L, "Günther", "Schmidt", "client1@mybanking.com", "Berlin Germany", "+4917657604588");
        Mockito.when(clientService.getCurrent()).thenReturn(client);
        Account accountForSave = new Account(null, "Test account", null, CurrencyCode.CHF);
        String iban = "DE12345678456783456699433444";
        Mockito.when(generateIban.generateAccountNumber()).thenReturn(iban);
        Mockito.when(repository.findById(iban)).thenReturn(Optional.empty());
        Account accountFromDatabase = new Account(iban, client, "Test account", AccountType.DEBIT, AccountStatus.ACTIVE,
                BigDecimal.valueOf(1000), CurrencyCode.CHF);
        Mockito.when(repository.save(Mockito.any())).thenReturn(accountFromDatabase);
        assertEquals(iban, accountService.create(accountForSave).getIban());
    }

    @Test
    void transactionHistory() {
        Client client = new Client(1L, "Günther", "Schmidt", "client1@mybanking.com", "Berlin Germany", "+4917657604588");
        Account accountOne = new Account("DE12345678456783456699433444", client, "Test account one", AccountType.DEBIT,
                AccountStatus.ACTIVE, BigDecimal.valueOf(1000), CurrencyCode.CHF);
        Account accountTwo = new Account("DE12345678456783454499874558", client, "Test account two", AccountType.DEBIT,
                AccountStatus.ACTIVE, BigDecimal.valueOf(1000), CurrencyCode.CHF);
        accountOne.setCreditTransactions(List.of(new Transaction(1l, accountOne, accountTwo, TransactionType.SUCCESS, BigDecimal.valueOf(500), "Transfer")));
        accountOne.setDebitTransactions(List.of(new Transaction(2l, accountTwo, accountOne, TransactionType.SUCCESS, BigDecimal.valueOf(300), "Return")));

        Mockito.when(clientService.getCurrent()).thenReturn(client);
        Mockito.when(repository.findByIbanAndClient(accountOne.getIban(), client))
                .thenReturn(Optional.of(accountOne));

        assertEquals(2, accountService.transactionHistory(accountOne.getIban()).size());

    }

    @Test
    void retrievingAccountBalance() {
        Client client = new Client(1L, "Günther", "Schmidt", "client1@mybanking.com", "Berlin Germany", "+4917657604588");
        Account account = new Account("DE12345678456783456699433444", client, "Test account", AccountType.DEBIT,
                AccountStatus.ACTIVE, BigDecimal.valueOf(1000), CurrencyCode.CHF);
        Mockito.when(clientService.getCurrent()).thenReturn(client);
        Mockito.when(repository.findByIbanAndClient(account.getIban(), client))
                .thenReturn(Optional.of(account));
        AccountBalanceInfoDto accountBalanceInfoDto = accountService.retrievingAccountBalance(account.getIban());
        assertEquals(1000, accountBalanceInfoDto.getAmount());
        assertEquals(CurrencyCode.CHF, accountBalanceInfoDto.getCurrency());
    }

    @Test
    void deleteAccountByIbanWhenIbanNotExists() {
        Mockito.when(repository.findById("DE1111111")).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.deleteAccountByIban("DE1111111"));
    }
}