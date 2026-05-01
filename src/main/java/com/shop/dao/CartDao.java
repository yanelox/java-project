package com.shop.dao;

import com.shop.model.CartItem;

import java.sql.SQLException;
import java.util.List;

public interface CartDao {
    List<CartItem> findByUserId(long userId) throws SQLException;

    void addItem(long userId, long productId, int quantity) throws SQLException;

    void updateQuantity(long userId, long productId, int quantity) throws SQLException;

    void removeItem(long userId, long productId) throws SQLException;

    void clearByUserId(long userId) throws SQLException;
}
