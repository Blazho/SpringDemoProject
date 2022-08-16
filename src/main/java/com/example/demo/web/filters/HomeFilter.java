package com.example.demo.web.filters;

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
        if (!path.equals("/home")){
            resp.sendRedirect("/home");
        }else {
            chain.doFilter(request, response);
        }
    }
}
