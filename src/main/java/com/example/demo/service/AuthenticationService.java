package com.example.demo.service;


import com.example.demo.model.User;

import java.util.Optional;

public interface AuthenticationService {

    public User logInUser(String username, String password);
    public Optional<User> findByUsername(String username);
    public Optional<User> findByUsernameAndPassword(String username, String password);
    public void register(String username, String name, String surname, String password, String rePassword);
}
