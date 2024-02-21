package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Account;
import org.example.entity.Transaction;
import org.example.model.dto.AccountBalanceInfoDto;
import org.example.model.dto.AccountDto;
import org.example.model.dto.TransactionDto;
import org.example.model.enums.CurrencyCode;
import org.example.model.enums.TransactionType;
import org.example.service.AccountService;
import org.example.service.dtoconverter.AccountDtoConverter;
import org.example.service.dtoconverter.TransactionDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

    @MockBean
    private AccountDtoConverter dtoConverter;

    @MockBean
    private TransactionDtoConverter transactionConverter;

    @MockBean
    private AccountService service;

    @Autowired
    private MockMvc mockMvc;

    private List<Account> accounts;
    private List<AccountDto> accountDtos;
    private List<Transaction> transactions;

    @BeforeEach
    void init() {
        Account account = new Account("DE12345678456783454499874558", "Test account", BigDecimal.valueOf(1000), CurrencyCode.CHF,
                null, null);
        Account accountTwo = new Account("DE12345678456783456699433444", "Test account", BigDecimal.valueOf(1000), CurrencyCode.CHF,
                null, null);
        accounts = List.of(account);
        accountDtos = List.of(new AccountDto(account.getIban(), null, null,
                account.getName(), account.getType(), account.getStatus(), account.getBalance()
                , account.getCurrencyCode(), account.getCreatedAt(), account.getUpdatedAt()));
        transactions = List.of(
                new Transaction(1l, account, accountTwo, TransactionType.SUCCESS, BigDecimal.valueOf(500), "Transfer"));
    }

    @Test
    void getAll() throws Exception {
        Account account = accounts.get(0);
        AccountDto accountDto = accountDtos.get(0);
        Mockito.when(service.getAll()).thenReturn(accounts);
        Mockito.when(dtoConverter.toDto(account))
                .thenReturn(accountDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(accountDtos)));
    }

    @Test
    void getTransactionsHistoryOfAccount() throws Exception {
        Account account = accounts.get(0);
        Transaction transaction = transactions.get(0);
        TransactionDto transactionDto = new TransactionDto(
                transaction.getId(),
                transaction.getDebitAccount().getIban(),
                transaction.getCreditAccount().getIban(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getDescription(), null);
        Mockito.when(service.transactionHistory(account.getIban())).thenReturn(transactions);
        Mockito.when(transactionConverter.toDto(transactions.get(0)))
                .thenReturn(transactionDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/history/" + account.getIban()).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(List.of(transactionDto))));

    }

    @Test
    void getByIban() throws Exception {
        Account account = accounts.get(0);
        AccountDto accountDto = accountDtos.get(0);
        Mockito.when(service.getByIban(account.getIban())).thenReturn(account);
        Mockito.when(dtoConverter.toDto(account)).thenReturn(accountDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/" + account.getIban()).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(accountDto)));
    }

    @Test
    void retrievingAccountBalance() throws Exception {
        Account account = accounts.get(0);
        Mockito.when(service.retrievingAccountBalance(account.getIban()))
                .thenReturn(new AccountBalanceInfoDto(account.getBalance().doubleValue(),
                        account.getCurrencyCode()));

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/balance/" + account.getIban()).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(new AccountBalanceInfoDto(account.getBalance().doubleValue(),
                        account.getCurrencyCode()))));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}