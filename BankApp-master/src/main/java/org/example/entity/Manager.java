package org.example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "managers")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name ist required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @OneToMany(mappedBy = "manager")
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "manager")
    private List<Product> products = new ArrayList<>();
    private int status;
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Manager() {
        //
    }

    public Manager(Long id, String firstName, String lastName, int status) {
        this(firstName, lastName, status);
        this.id = id;
    }

    public Manager(String firstName, String lastName, int status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.email = email(firstName, lastName);
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());

    }

    private String email(String firstName, String lastName) {
        String email = firstName.toLowerCase().concat(".").concat(lastName.toLowerCase())
                .concat("@mybanking.de");
        return email;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}