package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.model.enums.Role;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private String login;
    private Role userRole;

    public UserDto(String login, Role userRole) {
        this.login = login;
        this.userRole = userRole;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }
}