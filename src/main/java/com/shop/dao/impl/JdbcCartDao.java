package com.shop.dao.impl;

import com.shop.config.ConnectionFactory;
import com.shop.dao.CartDao;
import com.shop.model.CartItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCartDao implements CartDao {
    private static final String FIND_BY_USER_ID =
            "SELECT p.id AS product_id, p.name AS product_name, p.price, c.quantity " +
            "FROM cart c " +
            "JOIN products p ON p.id = c.product_id " +
            "WHERE c.user_id = ? " +
            "ORDER BY c.id DESC";
    private static final String ADD_ITEM =
            "INSERT INTO cart (user_id, product_id, quantity) " +
            "VALUES (?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";
    private static final String UPDATE_QUANTITY = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
    private static final String REMOVE_ITEM = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
    private static final String CLEAR_BY_USER = "DELETE FROM cart WHERE user_id = ?";

    @Override
    public List<CartItem> findByUserId(long userId) throws SQLException {
        List<CartItem> items = new ArrayList<>();
        try (var connection = ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(FIND_BY_USER_ID)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CartItem item = new CartItem();
                    item.setProductId(resultSet.getLong("product_id"));
                    item.setProductName(resultSet.getString("product_name"));
                    item.setPrice(resultSet.getBigDecimal("price"));
                    item.setQuantity(resultSet.getInt("quantity"));
                    items.add(item);
                }
            }
        }
        return items;
    }

    @Override
    public void addItem(long userId, long productId, int quantity) throws SQLException {
        try (var connection = ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(ADD_ITEM)) {
            statement.setLong(1, userId);
            statement.setLong(2, productId);
            statement.setInt(3, quantity);
            statement.executeUpdate();
        }
    }

    @Override
    public void updateQuantity(long userId, long productId, int quantity) throws SQLException {
        try (var connection = ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(UPDATE_QUANTITY)) {
            statement.setInt(1, quantity);
            statement.setLong(2, userId);
            statement.setLong(3, productId);
            statement.executeUpdate();
        }
    }

    @Override
    public void removeItem(long userId, long productId) throws SQLException {
        try (var connection = ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(REMOVE_ITEM)) {
            statement.setLong(1, userId);
            statement.setLong(2, productId);
            statement.executeUpdate();
        }
    }

    @Override
    public void clearByUserId(long userId) throws SQLException {
        try (var connection = ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(CLEAR_BY_USER)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        }
    }
}
