package com.example.demo.web.filters;

import com.example.demo.model.User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@Component
@WebFilter(filterName = "HomeFilter")
public class HomeFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();
        User user = (User) req.getSession().getAttribute("user");
        if (!path.equals("/home") &&
                !path.equals("/home/login") &&
                !path.equals("/contact") &&
                !path.equals("/register") &&
                !path.equals("/main.css") &&
                !path.equals("/main.js") &&
                !path.equals("/img/blue-background.png") &&
                user == null){
            resp.sendRedirect("/home");
        }else {
            chain.doFilter(request, response);
        }
    }
}
