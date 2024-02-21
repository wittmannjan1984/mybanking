package org.example.service;

import org.example.entity.Account;
import org.example.entity.Client;
import org.example.entity.Transaction;
import org.example.exception.UnsupportedTransactionException;
import org.example.model.enums.AccountStatus;
import org.example.model.enums.AccountType;
import org.example.model.enums.CurrencyCode;
import org.example.model.enums.TransactionType;
import org.example.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private UserService userService;

    @Mock
    private CurrencyApiService currencyApiService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private List<Account> accounts;
    private List<Transaction> transactions;

    @BeforeEach
    void init() {
        Client client = new Client(1L, "Günther", "Schmidt", "client1@mybanking.com", "Berlin Germany", "+4917657604588");
        accounts = List.of(
                new Account("DE12345678456783454499874558", client, "Test account", AccountType.DEBIT, AccountStatus.ACTIVE,
                        BigDecimal.valueOf(1000), CurrencyCode.CHF),
                new Account("DE98765662266565556444654321", "Test account", BigDecimal.valueOf(1000), CurrencyCode.CHF));

        transactions = List.of(
                new Transaction(1l, accounts.get(0), accounts.get(1), TransactionType.SUCCESS, BigDecimal.valueOf(500), "Transfer"),
                new Transaction(2l, accounts.get(1), accounts.get(0), TransactionType.SUCCESS, BigDecimal.valueOf(300), "Return")
        );
    }


    @Test
    void getByIdWhenExists() {
        Transaction transaction = transactions.get(0);
        Mockito.when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.ofNullable(transaction));
        assertEquals(1l, transactionService.getById(transaction.getId()).getId());
    }

    @Test
    void getByIdWhenNotExists() {
        Transaction transaction = transactions.get(0);
        Mockito.when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(UnsupportedTransactionException.class, () -> transactionService.getById(transaction.getId()));
    }

    @Test
    void transferMoneyBetweenAccounts() {
        Account accountFrom = accounts.get(0);
        String ibanFrom = accountFrom.getIban();
        Mockito.when(accountService.getByIban(ibanFrom)).thenReturn(accountFrom);

        Client client = accountFrom.getClient();
        Mockito.when(userService.isAuthorize(client.getEmail())).thenReturn(true);

        Account accountTo = accounts.get(1);
        String ibanTo = accountTo.getIban();
        Mockito.when(accountService.getByIban(ibanTo)).thenReturn(accountTo);

        Map<String, Double> ratesMap = Map.of(accountFrom.getCurrencyCode().name(), 1.0);
        Mockito.when(currencyApiService.getCurrencyMap(accountFrom.getCurrencyCode().name())).thenReturn(ratesMap);

        Transaction transactionAfterSave = new Transaction(accountFrom, accountTo, TransactionType.SUCCESS, BigDecimal.valueOf(300), "Transfer");
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transactionAfterSave);

        Transaction transaction = transactionService.transferMoneyBetweenAccounts(ibanFrom, ibanTo, 300.0, "Transfer");
        assertEquals(ibanFrom, transaction.getDebitAccount().getIban());
    }

    @Test
    void transferMoneyBetweenAccountsWhenAccountFromDoesNotBelongToClient() {
        Account accountFrom = accounts.get(0);
        String ibanFrom = accountFrom.getIban();
        Mockito.when(accountService.getByIban(ibanFrom)).thenReturn(accountFrom);

        Client client = accountFrom.getClient();
        Mockito.when(userService.isAuthorize(client.getEmail())).thenReturn(false);

        Account accountTo = accounts.get(1);
        String ibanTo = accountTo.getIban();

        assertThrows(UnsupportedTransactionException.class, () -> transactionService.transferMoneyBetweenAccounts(ibanFrom, ibanTo, 300.0, "Transfer"));
    }

    @Test
    void transferMoneyBetweenAccountsWhenMoneyNotEnough() {
        Account accountFrom = accounts.get(0);
        String ibanFrom = accountFrom.getIban();
        Mockito.when(accountService.getByIban(ibanFrom)).thenReturn(accountFrom);

        Client client = accountFrom.getClient();
        Mockito.when(userService.isAuthorize(client.getEmail())).thenReturn(true);

        Account accountTo = accounts.get(1);
        String ibanTo = accountTo.getIban();

        assertThrows(UnsupportedTransactionException.class,
                () -> transactionService.transferMoneyBetweenAccounts(ibanFrom, ibanTo, 4400.0, "Transfer"));

    }
}