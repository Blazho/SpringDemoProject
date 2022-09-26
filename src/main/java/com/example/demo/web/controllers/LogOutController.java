package com.example.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/logout")
public class LogOutController {

    @GetMapping
    public String logOutUser(HttpServletRequest req){
        req.getSession().invalidate();
        return "redirect:/home";
    }
}
