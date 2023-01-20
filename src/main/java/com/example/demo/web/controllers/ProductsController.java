package com.example.demo.web.controllers;

import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProductsPage(@RequestParam(required = false) String error,
                                  @RequestParam(required = false) String product,
                                  @RequestParam(required = false) Boolean deleted,
                                  Model model){
        if (product !=null && !product.isEmpty()){
            model.addAttribute("productMessage", true);
            model.addAttribute("product", product);
        }
        if (error !=null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        if (deleted !=null && deleted){
            model.addAttribute("hasDeleted", true);
            model.addAttribute("message", "Product has been deleted");
        }
        model.addAttribute("bodyContent","products");
        model.addAttribute("products", this.productService.findAll());
        return "master-template";
    }
}
