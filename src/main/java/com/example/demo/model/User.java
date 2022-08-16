package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_table")
public class User {

    @Id
    private String username;

    private String name;

    private String surname;

    private String password;

    public User() {
    }

    public User(String username, String name, String surname, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }
}
