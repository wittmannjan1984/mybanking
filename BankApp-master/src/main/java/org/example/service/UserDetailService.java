package org.example.service;

import org.example.entity.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userDataEntity = userService.findByLogin(username).
                orElseThrow(() -> new UsernameNotFoundException("User with login " + username + " not found"));

        return new User(userDataEntity.getLogin(), userDataEntity.getPassword(),
                List.of(new SimpleGrantedAuthority(userDataEntity.getUserRole().name())));
    }
}