package org.example.model.dto;

import org.example.model.enums.Role;

public class UserUpdateDto {

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "UserUpdateDto{" +
                "login='" + login + '\'' +
                '}';
    }
}