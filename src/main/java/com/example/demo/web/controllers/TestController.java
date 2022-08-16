package com.example.demo.web.controllers;


import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class TestController {

    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;
    private final AuthenticationService authenticationService;
    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;

    public TestController(CategoryService categoryService, ManufacturerService manufacturerService, AuthenticationService authenticationService, ProductService productService, ShoppingCartService shoppingCartService) {
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
        this.authenticationService = authenticationService;
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getPage(@RequestParam(required = false) String error,
                          @RequestParam(required = false) String id,
                          Model model){
        if (error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        if (id != null && !id.isEmpty()){
            Optional<Product> product = this.productService.findById(Long.parseLong(id));
            product.ifPresent(value -> model.addAttribute("product", value));
        }
        model.addAttribute("categories", this.categoryService.findAll());
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("products", this.productService.findAll());
        return "home";
    }
    @GetMapping("/{id}")
    public String redirectPage(@PathVariable String id){
        return "redirect:/home?id=" + id;
    }
    @PostMapping("/categories/add")
    public String addCategories(@RequestParam String cat_name,
                                @RequestParam String cat_desc){
        this.categoryService.add(cat_name, cat_desc);
        return "redirect:/home";
    }
    @PostMapping("/manufacturer/add")
    public String addManufacturer(@RequestParam String manu_name,
                                @RequestParam String manu_desc){
        this.manufacturerService.add(manu_name, manu_desc);
        return "redirect:/home";
    }
    @PostMapping("/user/login")
    public String logInUser(@RequestParam String user_username_login,
                            @RequestParam String user_password_login,
                            HttpServletRequest req){
        User user = null;
        try {
            user = this.authenticationService.logInUser(user_username_login, user_password_login);
            req.getSession().setAttribute("user", user);
            return "redirect:/home";
        }catch (RuntimeException ex){
            return "redirect:/home?error=" + ex.getMessage();
        }
    }
    @GetMapping("/user/logout")
    public String logOutUser(HttpServletRequest req){
        req.getSession().invalidate();
        return "redirect:/home";
    }
    @PostMapping("/user/register")
    public String registerUser(@RequestParam String user_username_register,
                               @RequestParam String user_name_register,
                               @RequestParam String user_surname_register,
                               @RequestParam String user_password_register,
                               @RequestParam String user_rePassword_register){
        User user = null;
        try {
            this.authenticationService.register(
                    user_username_register,
                    user_name_register,
                    user_surname_register,
                    user_password_register,
                    user_rePassword_register);
            return "redirect:/home";
        }catch (RuntimeException ex){
            return "redirect:/home?error=" + ex.getMessage();
        }
    }
    @PostMapping("/product/add")
    public String addProduct(@RequestParam(required = false) String product_name,
                             @RequestParam(required = false) Integer product_quantity,
                             @RequestParam(required = false) Integer product_price,
                             @RequestParam Long product_category,
                             @RequestParam Long product_manufacturer){
        try {
            this.productService.add(product_name, product_quantity, product_price, product_category, product_manufacturer);
            return "redirect:/home";
        }catch (RuntimeException ex){
            return "redirect:/home?error=" + ex.getMessage();
        }
    }
    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable String id){
        try {
            this.productService.deleteById(Long.parseLong(id));
            return "redirect:/home";
        }catch (RuntimeException ex){
            return "redirect:/home?error=" + ex.getMessage();
        }
    }
    @GetMapping("/shoppingcart")
    public String getCartPage(Model model, HttpServletRequest req){
        if (req.getSession().getAttribute("user") == null){
            model.addAttribute("cart_products", null);
        }else {
            User user = (User) req.getSession().getAttribute("user");
            model.addAttribute("cart_products",
                    this.shoppingCartService.findUsersCart(user.getUsername()).getProductList());
        }
        return "cart";
    }

    @PostMapping("/shoppingcart/add/{id}")
    public String addProductToCart(@PathVariable Long id, HttpServletRequest req){
        try {
            User user = (User) req.getSession().getAttribute("user");
            this.shoppingCartService.addProduct(id, user.getUsername());
            return "redirect:/home/shoppingcart";
        }catch (RuntimeException ex){
            return "redirect:/home?error=" + ex.getMessage();
        }
    }
    @PostMapping("/shoppingcart/delete/{id}")
    public String deleteProductFromCart(@PathVariable Long id, HttpServletRequest req){
        try {
            User user = (User) req.getSession().getAttribute("user");
            this.shoppingCartService.deleteProduct(id, user.getUsername());
            return "redirect:/home/shoppingcart";
        }catch (RuntimeException ex){
            return "redirect:/home?error=" + ex.getMessage();
        }
    }
}
