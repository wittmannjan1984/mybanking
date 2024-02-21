package org.example.model.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ManagerDto {

    @Hidden
    private long id;
    @Schema(description = "Managers first name", defaultValue = "Max")
    private String firstName;
    @Schema(description = "Managers last name", defaultValue = "Mustermann")
    private String lastName;
    @Hidden
    private int status;
    @Schema(description = "Managers email", defaultValue = "max.mustermann@mybanking.com")
    private String email;
    @Hidden
    private List<String> clientsName;
    @Hidden
    private List<String> products;
    @Hidden
    private LocalDateTime createdAt;
    @Hidden
    private LocalDateTime updatedAt;

    public ManagerDto(long id, String firstName, String lastName, int status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public ManagerDto(long id, String firstName, String lastName, int status, String email,
                      List<String> clientsName, List<String> products, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.email = email;
        this.clientsName = clientsName;
        this.products = products;
        this.createdAt = createdAt.toLocalDateTime();
        this.updatedAt = updatedAt.toLocalDateTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getClientsName() {
        return clientsName;
    }

    public void setClientsName(List<String> clientsName) {
        this.clientsName = clientsName;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "ManagerDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                ", clientsName=" + clientsName +
                ", products=" + products +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}