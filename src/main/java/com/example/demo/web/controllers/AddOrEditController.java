package com.example.demo.web.controllers;

import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ManufacturerService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class AddOrEditController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    public AddOrEditController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/add")
    public String getEditPage(@RequestParam(required = false) Long productId,
                              @RequestParam(required = false) String product,
                              Model model){
        if (product !=null && !product.isEmpty()){
            model.addAttribute("productMessage", true);
            model.addAttribute("created", product);
        }
        if (productId != null){
            Optional<Product> prod = this.productService.findById(productId);
            if (prod.isPresent()){
                model.addAttribute("product", prod.get());
            }
            else {
                return "redirect:/products?error=ProductNotFound";
            }
        }
        model.addAttribute("bodyContent", "add-product");
        model.addAttribute("categories", this.categoryService.findAll());
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        return "master-template";
    }
    @PostMapping("/delete")
    public String deleteProduct(@RequestParam Long productId){
        if (productId == null){
            return "redirect:/products?error=ProductNotFound";
        }
        Optional<Product> product = this.productService.findById(productId);
        if (product.isEmpty())
        {
            return "redirect:/products?error=ProductNotFound";
        }
        try {
            this.productService.deleteById(productId);
            return "redirect:/products?deleted=true";
        }catch (RuntimeException ex){
            return "redirect:/products?error=" + ex.getMessage();
        }


    }
    @PostMapping("/created")
    public String createOrEditProduct(@RequestParam String productName,
                                      @RequestParam(required = false) Integer productQuantity,
                                      @RequestParam(required = false) Integer productPrice,
                                      @RequestParam Long productCategory,
                                      @RequestParam Long productManufacturer){
        Optional<Product> product = null;
        try {
            product = this.productService.add(productName,
                    productQuantity,
                    productPrice,
                    productCategory,
                    productManufacturer);
            return "redirect:/products?product=ProductAddedOrEdited";
        }catch (RuntimeException ex){
            return "redirect:/products/add?product=" + ex.getMessage();
        }
    }
}
