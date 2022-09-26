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
        String user = req.getRemoteUser();
        if (user == null || user.equals("")){
            model.addAttribute("hasError", true);
            model.addAttribute("error", "User is not logged in!");
        }else {
            model.addAttribute("shoppingCartProducts", this.shoppingCartService.findUsersCart(user).getProductList());
        }
        model.addAttribute("bodyContent", "shoppingcart");
        return "master-template";
    }
    @PostMapping("/delete")
    public String deleteCartProduct(@RequestParam Long productId, HttpServletRequest req){
        String user = req.getRemoteUser();
        if (user == null || user.equals("")){
            return "redirect:/home?error=UserIsNotLoggedIn";
        }
        this.shoppingCartService.deleteProduct(productId, user);
        return "redirect:/shoppingcart";
    }
    @PostMapping("/add")
    public String addProductToCart(@RequestParam Long productId, HttpServletRequest req){
        String user = req.getRemoteUser();
        if (user == null || user.equals("")){
            return "redirect:/home?error=UserIsNotLoggedIn";
        }
        try {
            this.shoppingCartService.addProduct(productId, user);
            return "redirect:/products?product=ProductAddedToShopingCart";
        }catch (RuntimeException ex){
            return "redirect:/product?error" + ex.getMessage();
        }

    }
}
