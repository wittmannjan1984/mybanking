package org.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ClientUpdateDto {

    @Schema(description = "Clients address", defaultValue = "Germany, Berlin, Alexander Platz 1")
    private String address;
    @Schema(description = "Clients phone", defaultValue = "+17584487833")
    private String phone;
    @Schema(description = "Clients tax code", defaultValue = "78922ER")
    private String taxCode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @Override
    public String toString() {
        return "ClientUpdateDto{" +
                "address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", taxCode='" + taxCode + '\'' +
                '}';
    }
}