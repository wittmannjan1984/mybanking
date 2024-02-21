package org.example.service;

import org.example.entity.Account;
import org.example.entity.Client;
import org.example.entity.Transaction;
import org.example.exception.UnsupportedTransactionException;
import org.example.model.enums.TransactionType;
import org.example.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final static double RANGE = 50;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencyApiService currencyApiService;

    @Autowired
    private UserService userService;

    @Override
    public Transaction getById(long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new UnsupportedTransactionException(String.format("Transaction with id %d not found", id)));
    }

    @Override
    public List<Transaction> search(String iban, BigDecimal amount) {
        List<Transaction> transactionHistory = accountService.transactionHistory(iban);
        List<Transaction> transactions = transactionHistory.stream().
                filter(tr -> (tr.getAmount().compareTo(amount.subtract(BigDecimal.valueOf(RANGE))) > 0) &&
                        (tr.getAmount().compareTo(amount.add(BigDecimal.valueOf(RANGE))) < 0)).collect(Collectors.toList());
        if (transactions.isEmpty()) {
            throw new UnsupportedTransactionException("No transaction found");
        }
        return transactions;
    }

    @Transactional
    @Override
    public Transaction transferMoneyBetweenAccounts(String ibanFrom, String ibanTo, double amount, String description) {
        Account accountFrom = accountService.getByIban(ibanFrom);
        Client client = accountFrom.getClient();
        if (!userService.isAuthorize(client.getEmail())) {
            log.warn("Does not have right to make transfer from {} by client {} ", ibanFrom, client);
            throw new UnsupportedTransactionException("Wrong client");
        }

        if (accountFrom.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new UnsupportedTransactionException("You have not enough money on your account");
        }
        Account accountTo = accountService.getByIban(ibanTo);

        accountFrom.setBalance(accountFrom.getBalance().subtract(BigDecimal.valueOf(amount)));
        accountFrom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        Double currencyRate = getCurrencyRate(accountFrom.getCurrencyCode().name(), accountTo.getCurrencyCode().name());

        accountTo.setBalance(accountTo.getBalance().add(BigDecimal.valueOf(amount * currencyRate)));
        accountTo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        Transaction transaction = new Transaction(accountFrom, accountTo, TransactionType.SUCCESS, BigDecimal.valueOf(amount), description);
        Transaction transactionEntity = transactionRepository.save(transaction);
        log.debug("Transaction {}", transactionEntity);
        return transactionEntity;
    }

    private Double getCurrencyRate(String from, String to) {
        Map<String, Double> currencyMap = currencyApiService.getCurrencyMap(from);
        return currencyMap.get(to.toUpperCase());
    }
}