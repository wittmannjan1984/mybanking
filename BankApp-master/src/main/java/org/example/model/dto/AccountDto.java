package org.example.model.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.model.enums.AccountStatus;
import org.example.model.enums.AccountType;
import org.example.model.enums.CurrencyCode;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AccountDto {

    @Hidden
    private String iban;
    @Schema(description = "First name of client", defaultValue = "Axel")
    private String clientFirstName;
    @Schema(description = "Last name of client", defaultValue = "Parker")
    private String clientLastName;
    @Schema(description = "Name of account", defaultValue = "My account")
    private String name;
    @Schema(description = "Type of account - debit or credit", defaultValue = "DEBIT")
    private AccountType type;
    @Schema(description = "Status of account - active or blocked", defaultValue = "ACTIVE")
    private AccountStatus status;
    @Hidden
    private BigDecimal balance;
    @Schema(description = "Account currency", defaultValue = "EUR")
    private CurrencyCode currencyCode;
    @Hidden
    private Timestamp createdAt;
    @Hidden
    private Timestamp updatedAt;

    public AccountDto(String iban, String clientFirstName, String clientLastName, String name, AccountType type,
                      AccountStatus status, BigDecimal balance,
                      CurrencyCode currencyCode,Timestamp createdAt, Timestamp updatedAt) {
        this.iban = iban;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.name = name;
        this.type = type;
        this.status = status;
        this.balance = balance;
        this.currencyCode = currencyCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "iban='" + iban + '\'' +
                ", clientFirstName='" + clientFirstName + '\'' +
                ", clientLastName='" + clientLastName + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", balance=" + balance +
                ", currencyCode=" + currencyCode +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}