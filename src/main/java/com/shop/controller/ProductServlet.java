package com.shop.controller;

import com.shop.service.ProductService;
import com.shop.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ProductServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        try {
            if ("/product".equals(servletPath)) {
                showDetails(request, response);
            } else {
                showList(request, response);
            }
        } catch (SQLException exception) {
            throw new ServletException(exception);
        }
    }

    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Long categoryId = ServletUtil.parseLong(request.getParameter("categoryId"));
        request.setAttribute("categories", productService.findAllCategories());
        request.setAttribute("selectedCategoryId", categoryId);
        request.setAttribute(
                "products",
                categoryId == null ? productService.findAllProducts() : productService.findProductsByCategory(categoryId)
        );
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }

    private void showDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Long productId = ServletUtil.parseLong(request.getParameter("id"));
        if (productId == null) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }
        var product = productService.findProductById(productId);
        if (product.isEmpty()) {
            request.setAttribute("error", "Product not found.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("product", product.get());
        request.getRequestDispatcher("/WEB-INF/views/product-details.jsp").forward(request, response);
    }
}
