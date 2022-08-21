package com.example.demo.web.controllers;

import com.example.demo.model.User;
import com.example.demo.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
    @GetMapping
    public String getShoppingCartPage(Model model, HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("hasError", true);
            model.addAttribute("error", "User is not logged in!");
        }else {
            model.addAttribute("shoppingCartProducts", this.shoppingCartService.findUsersCart(user.getUsername()).getProductList());
        }
        model.addAttribute("bodyContent", "shoppingcart");
        return "master-template";
    }
    @PostMapping("/delete")
    public String deleteCartProduct(@RequestParam Long productId, HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/home?error=UserIsNotLoggedIn";
        }
        this.shoppingCartService.deleteProduct(productId, user.getUsername());
        return "redirect:/shoppingcart";
    }
    @PostMapping("/add")
    public String addProductToCart(@RequestParam Long productId, HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/home?error=UserIsNotLoggedIn";
        }
        try {
            this.shoppingCartService.addProduct(productId, user.getUsername());
            return "redirect:/products?product=ProductAddedToShopingCart";
        }catch (RuntimeException ex){
            return "redirect:/product?error" + ex.getMessage();
        }

    }
}
