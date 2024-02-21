package org.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.model.enums.TransactionType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TransactionDto {

    private long id;
    private final String debitAccountIban;
    private final String creditAccountIban;
    private final TransactionType transactionType;
    private final BigDecimal amount;
    private final String description;
    private final Timestamp createdAt;

    public TransactionDto(long id, String debitAccountIban, String creditAccountIban,
                          TransactionType transactionType, BigDecimal amount, String description) {
        this.id = id;
        this.debitAccountIban = debitAccountIban;
        this.creditAccountIban = creditAccountIban;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public TransactionDto(long id, String debitAccountIban, String creditAccountIban,
                          TransactionType transactionType, BigDecimal amount, String description, Timestamp createdAt) {
        this.id = id;
        this.debitAccountIban = debitAccountIban;
        this.creditAccountIban = creditAccountIban;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDebitAccountIban() {
        return debitAccountIban;
    }

    public String getCreditAccountIban() {
        return creditAccountIban;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", debitAccount=" + debitAccountIban +
                ", creditAccount=" + creditAccountIban +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}