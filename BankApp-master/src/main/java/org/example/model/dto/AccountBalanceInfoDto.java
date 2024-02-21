package org.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.model.enums.CurrencyCode;

public class AccountBalanceInfoDto {

    @Schema(description = "Amount of account", defaultValue = "10000 EUR")
    private Double amount;
    @Schema(description = "Currency of account", defaultValue = "EUR")
    private CurrencyCode currency;

    public AccountBalanceInfoDto(Double amount, CurrencyCode currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }
}