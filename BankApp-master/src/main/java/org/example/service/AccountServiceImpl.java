package org.example.service;

import org.example.entity.Account;
import org.example.entity.Client;
import org.example.entity.Transaction;
import org.example.exception.AccountExistsException;
import org.example.exception.AccountNotFoundException;
import org.example.model.dto.AccountBalanceInfoDto;
import org.example.model.enums.AccountStatus;
import org.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private GenerateIban generateIban;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAllByCurrentClient() {
        return accountRepository.findAllByClient(clientService.getCurrent());
    }

    @Override
    public Account getByIban(String iban) {
        return accountRepository.findById(iban)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account with IBAN %s not found", iban)));
    }

    @Override
    public Account create(Account account) {
        Client loggedClient = clientService.getCurrent();
        account.setIban(generateIban.generateAccountNumber());
        if (accountRepository.findById(account.getIban()).isPresent()) {
            throw new AccountExistsException("This account already exists");
        }

        account.setClient(loggedClient);
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(BigDecimal.valueOf(0));
        return accountRepository.save(account);
    }

    @Override
    public List<Transaction> transactionHistory(String iban) {
        Account account = accountRepository.findByIbanAndClient(iban, clientService.getCurrent())
                .orElseThrow(() -> new AccountNotFoundException("Account doesn't belong to you"));
        return Stream.of(account.getDebitTransactions(), account.getCreditTransactions())
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Transaction::getCreatedAt))
                .collect(Collectors.toList());
    }

    @Override
    public AccountBalanceInfoDto retrievingAccountBalance(String iban) {
        Account account = accountRepository.findByIbanAndClient(iban, clientService.getCurrent())
                .orElseThrow(() -> new AccountNotFoundException("Account doesn't belong to you"));
        return new AccountBalanceInfoDto(account.getBalance().doubleValue(), account.getCurrencyCode());
    }

    @Override
    public void deleteAccountByIban(String iban) {
        Account account = getByIban(iban);
        if (account.getBalance().compareTo(BigDecimal.valueOf(0)) > 0) {
            throw new UnsupportedOperationException("Deletion is not possible cause account balance is positive");
        }
        accountRepository.delete(getByIban(iban));
    }
}