package com.example.demo.web.controllers;

import com.example.demo.model.User;
import com.example.demo.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AuthenticationService authenticationService;

    public RegisterController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @GetMapping
    public String getRegisterPage(Model model,
                                  @RequestParam(required = false) String error){
        if (error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "register");
        return "master-template";
    }
    @PostMapping("/user")
    public String registerUser(@RequestParam String registerUsername,
                               @RequestParam String registerName,
                               @RequestParam String registerSurname,
                               @RequestParam String registerPassword,
                               @RequestParam String registerRePassword){
        try {
            this.authenticationService.register(registerUsername, registerName, registerSurname, registerPassword, registerRePassword);
            return "redirect:/home";
        }catch (RuntimeException ex){
            return "redirect:/register?error="+ ex.getMessage();
        }
    }
}
