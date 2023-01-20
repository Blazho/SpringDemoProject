package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.exceptions.NameNullOrEmptyException;
import com.example.demo.respository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpls implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpls(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<Category> add(String name, String address) {
        if (name == null || name.isEmpty()){
            throw new NameNullOrEmptyException();
        }
        return Optional.of(this.categoryRepository.save(new Category(name, address)));
    }

    @Override
    public Optional<Category> findById(Long id) {
        return this.categoryRepository.findById(id);
    }

    @Override
    public void delteById(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
