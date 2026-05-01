package com.shop.filter;

import com.shop.util.ServletUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class AuthFilter implements Filter {
    private static final Set<String> PROTECTED_PATHS = Set.of(
            "/cart",
            "/cart/add",
            "/cart/remove",
            "/cart/update",
            "/checkout",
            "/orders",
            "/logout"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (!PROTECTED_PATHS.contains(path) || ServletUtil.getCurrentUser(httpRequest) != null) {
            chain.doFilter(request, response);
            return;
        }
        ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/login");
    }
}
