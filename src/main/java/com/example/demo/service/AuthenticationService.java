package com.example.demo.service;


import com.example.demo.model.User;
import com.example.demo.model.enumeration.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AuthenticationService extends UserDetailsService {

    public User logInUser(String username, String password);
    public Optional<User> findByUsernameAndPassword(String username, String password);
    public void register(String username, String name, String surname, String password, String rePassword, Role role);
}
