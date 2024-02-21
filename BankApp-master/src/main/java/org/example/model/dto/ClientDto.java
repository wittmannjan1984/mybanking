package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.model.enums.UserStatus;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {

    @Hidden
    private long id;
    @Hidden
    private String managersFirstName;
    @Hidden
    private String managersSecondName;
    @Hidden
    private UserStatus status;
    @Schema(description = "Clients tax code", defaultValue = "78922ER")
    private String taxCode;
    @Schema(description = "Clients first name", defaultValue = "John")
    private String firstName;
    @Schema(description = "Clients last name", defaultValue = "Smith")
    private String lastName;
    @Schema(description = "Clients email", defaultValue = "john.smith@google.com")
    private String email;
    @Schema(description = "Clients address", defaultValue = "Germany, Berlin, Alexander Platz 1")
    private String address;
    @Schema(description = "Clients phone", defaultValue = "+17584487833")
    private String phone;
    @Hidden
    private Timestamp createdAt;
    @Hidden
    private Timestamp updatedAt;

    public ClientDto(long id, String managersFirstName,
                     String managersSecondName,
                     UserStatus status, String taxCode,
                     String firstName, String lastName, String email, String address,
                     String phone, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.managersFirstName = managersFirstName;
        this.managersSecondName = managersSecondName;
        this.status = status;
        this.taxCode = taxCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getManagersFirstName() {
        return managersFirstName;
    }

    public void setManagersFirstName(String managersFirstName) {
        this.managersFirstName = managersFirstName;
    }

    public String getManagersSecondName() {
        return managersSecondName;
    }

    public void setManagersSecondName(String managersSecondName) {
        this.managersSecondName = managersSecondName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "managersFirstName='" + managersFirstName + '\'' +
                ", managersSecondName='" + managersSecondName + '\'' +
                ", status=" + status +
                ", taxCode='" + taxCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}