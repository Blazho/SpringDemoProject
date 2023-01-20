package com.example.demo.service;

import com.example.demo.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public List<Product> findAll();

    public Optional<Product> add(String name, Integer quantity, Integer price, Long categoryId, Long manufacturerId);

    public Optional<Product> findById(Long id);

    public void deleteById(Long id);
}
