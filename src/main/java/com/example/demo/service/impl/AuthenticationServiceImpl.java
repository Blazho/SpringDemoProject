package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.model.exceptions.IllegalUserCredentialsException;
import com.example.demo.model.exceptions.PasswordsDoNotMatchException;
import com.example.demo.model.exceptions.UserAlreadyExistException;
import com.example.demo.model.exceptions.UserNotFoundException;
import com.example.demo.respository.UserRepository;
import com.example.demo.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User logInUser(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new IllegalUserCredentialsException();
        }
        Optional<User> user = this.findByUsernameAndPassword(username, password);
        if (user.isEmpty()){
            throw new UserNotFoundException();
        }else {
            return user.get();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findById(username);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return this.userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public void register(String username, String name, String surname, String password, String rePassword) {
        if (
                username == null || username.isEmpty() ||
                        name == null || name.isEmpty() ||
                        surname == null || surname.isEmpty() ||
                        password == null || password.isEmpty() ||
                        rePassword == null || rePassword.isEmpty()
        ){
            throw new IllegalUserCredentialsException();
        }
        if (this.findByUsername(username).isPresent()){
            throw new UserAlreadyExistException();
        }
        if (!password.equals(rePassword)){
            throw new PasswordsDoNotMatchException();
        }
        this.userRepository.save(new User(username, name, surname, password));
    }
}
