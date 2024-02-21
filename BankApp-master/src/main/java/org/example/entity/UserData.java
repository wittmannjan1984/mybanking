package org.example.entity;

import org.example.model.enums.Role;

import javax.persistence.*;

@Entity
@Table(name = "user_data")
public class UserData {

    @Id
    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role userRole;

    public UserData() {
        //
    }

    public UserData(String login, String password) {
        this.login = login;
        this.password = password;
        this.userRole = Role.USER;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }
}