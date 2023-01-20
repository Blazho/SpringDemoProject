package com.example.demo.service;


import com.example.demo.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public List<Category> findAll();

    public Optional<Category> add(String name, String address);

    public Optional<Category> findById(Long id);

    public void delteById(Long id);
}
