package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.Product;
import com.example.demo.model.exceptions.CategoryNotFoundException;
import com.example.demo.model.exceptions.ManufacturerNotFoundException;
import com.example.demo.model.exceptions.MissingArgumentException;
import com.example.demo.respository.CategoryRepository;
import com.example.demo.respository.ManufacturerRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final CategoryRepository categoryRepository;
    private final ShoppingCartService shoppingCartService;

    public ProductServiceImpl(ProductRepository productRepository, ManufacturerRepository manufacturerRepository, CategoryRepository categoryRepository, ShoppingCartService shoppingCartService) {
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> add(String name, Integer quantity,
                                 Integer price, Long categoryId, Long manufacturerId) {
        if (name == null || name.isEmpty() ||
            quantity == null ||
            price == null){
            throw new MissingArgumentException();
        }
        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow( () -> new ManufacturerNotFoundException(manufacturerId));
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow( () -> new CategoryNotFoundException(categoryId));
        return Optional.of(this.productRepository.save(new Product(name, quantity, price, category, manufacturer)));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }
}
