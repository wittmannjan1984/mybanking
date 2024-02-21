package org.example.model.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.entity.Manager;
import org.example.model.enums.CurrencyCode;
import org.example.model.enums.ProductStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class ProductDto {

    @Hidden
    private long id;
    @Schema(description = "Managers first name", defaultValue = "Max")
    private String managerFirstName;
    @Schema(description = "Managers last name", defaultValue = "Mustermann")
    private String managerLastName;
    private String name;
    private ProductStatus status;
    private CurrencyCode currencyCode;
    private double interestRate;
    private int productLimit;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public ProductDto() {
        //
    }

    public ProductDto(long id, String managerFirstName, String managerLastName, String name,
                      ProductStatus status, CurrencyCode currencyCode, double interestRate, int productLimit) {
        this.id = id;
        this.managerFirstName = managerFirstName;
        this.managerLastName = managerLastName;
        this.name = name;
        this.status = status;
        this.currencyCode = currencyCode;
        this.interestRate = interestRate;
        this.productLimit = productLimit;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getManagerFirstName() {
        return managerFirstName;
    }

    public void setManagerFirstName(String managerFirstName) {
        this.managerFirstName = managerFirstName;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(int productLimit) {
        this.productLimit = productLimit;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", managerFirstName='" + managerFirstName + '\'' +
                ", managerLastName='" + managerLastName + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", currencyCode=" + currencyCode +
                ", interestRate=" + interestRate +
                ", productLimit=" + productLimit +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}