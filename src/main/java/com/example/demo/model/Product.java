package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer quantity;

    private Integer price;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Manufacturer manufacturer;

    public Product() {
    }

    public Product(String name, Integer quantity, Integer price, Category category, Manufacturer manufacturer) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.manufacturer = manufacturer;
    }
}
