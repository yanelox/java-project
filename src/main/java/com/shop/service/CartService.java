package com.shop.service;

import com.shop.dao.CartDao;
import com.shop.dao.ProductDao;
import com.shop.dao.impl.JdbcCartDao;
import com.shop.dao.impl.JdbcProductDao;
import com.shop.model.CartItem;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class CartService {
    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService() {
        this(new JdbcCartDao(), new JdbcProductDao());
    }

    public CartService(CartDao cartDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    public List<CartItem> getCart(long userId) throws SQLException {
        return cartDao.findByUserId(userId);
    }

    public void addProduct(long userId, long productId, int quantity) throws SQLException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (productDao.findById(productId).isEmpty()) {
            throw new IllegalArgumentException("Product not found.");
        }
        cartDao.addItem(userId, productId, quantity);
    }

    public void updateQuantity(long userId, long productId, int quantity) throws SQLException {
        if (quantity <= 0) {
            cartDao.removeItem(userId, productId);
            return;
        }
        cartDao.updateQuantity(userId, productId, quantity);
    }

    public void removeProduct(long userId, long productId) throws SQLException {
        cartDao.removeItem(userId, productId);
    }

    public void clearCart(long userId) throws SQLException {
        cartDao.clearByUserId(userId);
    }

    public BigDecimal calculateTotal(List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }
}
