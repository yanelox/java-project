package com.shop.controller;

import com.shop.model.User;
import com.shop.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class AuthServlet extends HttpServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String view = "/WEB-INF/views/login.jsp";
        if ("/register".equals(servletPath)) {
            view = "/WEB-INF/views/register.jsp";
        }
        request.getRequestDispatcher(view).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        try {
            if ("/register".equals(servletPath)) {
                handleRegister(request, response);
            } else {
                handleLogin(request, response);
            }
        } catch (IllegalArgumentException | SQLException exception) {
            request.setAttribute("error", exception.getMessage());
            doGet(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        User user = authService.register(
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("password")
        );
        request.getSession().setAttribute("currentUser", user);
        response.sendRedirect(request.getContextPath() + "/products");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        Optional<User> user = authService.login(request.getParameter("email"), request.getParameter("password"));
        if (user.isEmpty()) {
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        request.getSession().setAttribute("currentUser", user.get());
        response.sendRedirect(request.getContextPath() + "/products");
    }
}
