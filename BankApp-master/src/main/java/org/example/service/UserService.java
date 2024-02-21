package org.example.service;

import org.example.entity.UserData;

import java.util.Optional;

public interface UserService {

    UserData create(UserData userData);

    Optional<UserData> findByLogin(String login);

    String getCurrentUserLogin();

    boolean isAuthorize(String login);

    void updateRoleToManager(String login);

    void updateRoleToClient(String login);
}