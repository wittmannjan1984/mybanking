package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GenerateIban {
    private final String BANK_CODE = "DE0055555000";

    public String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            accountNumber.append(ThreadLocalRandom.current().nextInt(0,10));
        }
        String accountIban = BANK_CODE.concat(accountNumber.toString());
        return accountIban;
    }
}