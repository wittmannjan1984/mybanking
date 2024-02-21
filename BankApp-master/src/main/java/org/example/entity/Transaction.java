package org.example.entity;

import org.example.model.enums.TransactionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "debit_account_id", referencedColumnName = "iban")
    private Account debitAccount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "credit_account_id", referencedColumnName = "iban")
    private Account creditAccount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private BigDecimal amount;
    private String description;
    private Timestamp createdAt;

    public Transaction() {
        //
    }

    public Transaction(Account debitAccount, Account creditAccount, TransactionType transactionType, BigDecimal amount, String description) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public Transaction(long id, Account debitAccount, Account creditAccount, TransactionType transactionType, BigDecimal amount, String description) {
        this(debitAccount, creditAccount, transactionType, amount, description);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Account getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(Account debitAccount) {
        this.debitAccount = debitAccount;
    }

    public Account getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(Account creditAccount) {
        this.creditAccount = creditAccount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", debitAccount=" + debitAccount +
                ", creditAccount=" + creditAccount +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}