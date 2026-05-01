package com.shop.controller;

import com.shop.model.User;
import com.shop.service.CartService;
import com.shop.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CartServlet extends HttpServlet {
    private final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = ServletUtil.getCurrentUser(request);
        try {
            List<com.shop.model.CartItem> items = cartService.getCart(currentUser.getId());
            request.setAttribute("cartItems", items);
            request.setAttribute("total", cartService.calculateTotal(items));
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
        } catch (SQLException exception) {
            throw new ServletException(exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = ServletUtil.getCurrentUser(request);
        String servletPath = request.getServletPath();
        Long productId = ServletUtil.parseLong(request.getParameter("productId"));
        if (productId == null) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        try {
            if ("/cart/add".equals(servletPath)) {
                int quantity = ServletUtil.parsePositiveInt(request.getParameter("quantity"), 1);
                cartService.addProduct(currentUser.getId(), productId, quantity);
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            if ("/cart/update".equals(servletPath)) {
                int quantity = ServletUtil.parsePositiveInt(request.getParameter("quantity"), 0);
                cartService.updateQuantity(currentUser.getId(), productId, quantity);
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            cartService.removeProduct(currentUser.getId(), productId);
            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (IllegalArgumentException | SQLException exception) {
            request.setAttribute("error", exception.getMessage());
            doGet(request, response);
        }
    }
}
