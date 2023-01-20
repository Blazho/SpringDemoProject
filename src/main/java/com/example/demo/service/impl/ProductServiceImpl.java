package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.Manufacturer;
import com.example.demo.model.Product;
import com.example.demo.model.ShoppingCart;
import com.example.demo.model.exceptions.CategoryNotFoundException;
import com.example.demo.model.exceptions.ManufacturerNotFoundException;
import com.example.demo.model.exceptions.MissingProductInformationException;
import com.example.demo.model.exceptions.ProductInShoppingCartException;
import com.example.demo.respository.CategoryRepository;
import com.example.demo.respository.ManufacturerRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.respository.ShoppingCartRepository;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final CategoryRepository categoryRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    public ProductServiceImpl(ProductRepository productRepository, ManufacturerRepository manufacturerRepository, CategoryRepository categoryRepository, ShoppingCartRepository shoppingCartRepository) {
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
        this.shoppingCartRepository = shoppingCartRepository;
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
            price == null ||
            categoryId == null ||
            manufacturerId == null){
            throw new MissingProductInformationException();
        }
        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow( () -> new ManufacturerNotFoundException(manufacturerId));
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow( () -> new CategoryNotFoundException(categoryId));
        Optional<Product> product = this.productRepository.findByName(name);
        if (product.isPresent()){
            product.get().setQuantity(quantity);
            product.get().setPrice(price);
            product.get().setManufacturer(manufacturer);
            product.get().setCategory(category);
            return Optional.of(this.productRepository.save(product.get()));
        }
        return Optional.of(this.productRepository.save(new Product(name, quantity, price, category, manufacturer)));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        List<ShoppingCart> shoppingCarts = this.shoppingCartRepository.findAll();
        for (ShoppingCart cart : shoppingCarts){
            if (cart.getProductList().stream().anyMatch(r-> Objects.equals(r.getId(), id))){
                throw new ProductInShoppingCartException(id);
            }
        }
        this.productRepository.deleteById(id);
    }
}
