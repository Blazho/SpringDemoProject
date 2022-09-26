package com.example.demo.web.controllers;

import com.example.demo.model.User;
import com.example.demo.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LogInController {

    private final AuthenticationService authenticationService;

    public LogInController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    String getLogInPage(@RequestParam(required = false) String error,
                        Model model){
        if (error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "loginPage");
        return "master-template";
    }

    @PostMapping
    public String logInUser(@RequestParam String loginUsername,
                            @RequestParam String loginPassword,
                            Model model,
                            HttpServletRequest req){
        try {
            User user = this.authenticationService.logInUser(loginUsername, loginPassword);
            req.getSession().setAttribute("user", user);
            model.addAttribute("bodyContent", "index");
            return "redirect:/home";
        }catch (RuntimeException ex){
            return "redirect:/home?error=" + ex.getMessage();
        }
    }
}
