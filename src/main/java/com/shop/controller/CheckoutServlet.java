package com.shop.controller;

import com.shop.model.User;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CheckoutServlet extends HttpServlet {
    private final CartService cartService = new CartService();
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = ServletUtil.getCurrentUser(request);
        try {
            var items = cartService.getCart(currentUser.getId());
            request.setAttribute("cartItems", items);
            request.setAttribute("total", cartService.calculateTotal(items));
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
        } catch (SQLException exception) {
            throw new ServletException(exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = ServletUtil.getCurrentUser(request);
        try {
            orderService.placeOrder(currentUser.getId());
            response.sendRedirect(request.getContextPath() + "/orders");
        } catch (IllegalArgumentException | SQLException exception) {
            request.setAttribute("error", exception.getMessage());
            doGet(request, response);
        }
    }
}
