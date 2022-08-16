package com.example.demo.respository;

import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;
import com.example.demo.model.enumeration.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    public Optional<ShoppingCart> findByUser(User user);
}
