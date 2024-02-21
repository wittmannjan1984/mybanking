package org.example.service;

import org.example.entity.UserData;
import org.example.exception.UserNotFoundException;
import org.example.model.enums.Role;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserData create(UserData userData) {
        if (userRepository.findById(userData.getLogin()).isPresent()) {
            throw new EntityExistsException(String.format("User with login %s already exists",
                    userData.getLogin()));
        }
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        return userRepository.save(userData);
    }

    @Override
    public Optional<UserData> findByLogin(String login) {
        return userRepository.findById(login);
    }

    @Override
    public String getCurrentUserLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public boolean isAuthorize(String login) {
        return getCurrentUserLogin().equals(login);
    }

    @Override
    public void updateRoleToManager(String login) {
        UserData user = findByLogin(login).orElseThrow(() -> new UserNotFoundException("This user not found"));
        user.setUserRole(Role.MANAGER);
        userRepository.save(user);
    }

    public void removeRole(String role){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> updatedAuthorities =
                auth.getAuthorities().stream()
                        .filter(r -> !role.equals(r.getAuthority()))
                        .collect(Collectors.toList());

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public void updateRoleToClient(String login) {
        UserData user = findByLogin(login).orElseThrow(() -> new UserNotFoundException("This user not found"));
        user.setUserRole(Role.CLIENT);
        removeRole("USER");
        userRepository.save(user);
    }
}