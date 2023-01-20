package com.example.demo.model;

import com.example.demo.model.enumeration.ShoppingCartStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;

    @ManyToOne
    @JoinColumn(name = "user_username")
    private User user;

    @ManyToMany
    private List<Product> productList;

    public ShoppingCart() {
    }

    public ShoppingCart(User user) {
        this.user = user;
        this.dateCreated = LocalDateTime.now();
        this.status = ShoppingCartStatus.CREATED;
        this.productList = new ArrayList<>();
    }
}
