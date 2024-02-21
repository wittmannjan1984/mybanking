package org.example.service;

import org.example.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    Transaction getById(long id);

    List<Transaction> search(String iban, BigDecimal amount);

    Transaction transferMoneyBetweenAccounts(String ibanFrom, String ibanTo, double amount, String description);
}