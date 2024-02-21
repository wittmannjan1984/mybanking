package org.example.model.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.entity.Account;
import org.example.entity.Product;

import java.sql.Timestamp;
import java.util.Date;

public class AgreementDto {

    @Hidden
    private long id;
    @Hidden
    private AccountDto account;
    @Hidden
    private ProductDto product;
    @Schema(description = "Interest rate", defaultValue = "2.0")
    private double interestRate;
    @Hidden
    private int status;
    @Hidden
    private double sum;
    @Hidden
    private Timestamp createdAt;
    @Hidden
    private Timestamp updatedAt;

    public AgreementDto(AccountDto account, ProductDto product, double interestRate,
                        int status, double sum) {
        this.account = account;
        this.product = product;
        this.interestRate = interestRate;
        this.status = status;
        this.sum = sum;
        this.createdAt = new Timestamp(new Date().getTime());
        this.updatedAt = new Timestamp(new Date().getTime());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "id=" + id +
                ", account=" + account +
                ", product=" + product +
                ", interestRate=" + interestRate +
                ", status=" + status +
                ", sum=" + sum +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}