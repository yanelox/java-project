package com.shop.dao.impl;

import com.shop.dao.OrderDao;
import com.shop.model.Order;
import com.shop.model.OrderItem;
import com.shop.model.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcOrderDao implements OrderDao {
    private static final String INSERT_ORDER = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, ?)";
    private static final String INSERT_ORDER_ITEM =
            "INSERT INTO order_items (order_id, product_id, product_name, price, quantity) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_USER_ID = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC, id DESC";
    private static final String FIND_ITEMS_BY_ORDER_ID = "SELECT * FROM order_items WHERE order_id = ? ORDER BY id";

    @Override
    public long createOrder(Connection connection, Order order) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.setBigDecimal(2, order.getTotalAmount());
            statement.setString(3, order.getStatus().name());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }
        throw new SQLException("Failed to create order");
    }

    @Override
    public void createOrderItems(Connection connection, long orderId, List<OrderItem> items) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_ITEM)) {
            for (OrderItem item : items) {
                statement.setLong(1, orderId);
                statement.setLong(2, item.getProductId());
                statement.setString(3, item.getProductName());
                statement.setBigDecimal(4, item.getPrice());
                statement.setInt(5, item.getQuantity());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    @Override
    public List<Order> findByUserId(long userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (var connection = com.shop.config.ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(FIND_BY_USER_ID)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getLong("id"));
                    order.setUserId(resultSet.getLong("user_id"));
                    order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
                    order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
                    var timestamp = resultSet.getTimestamp("created_at");
                    if (timestamp != null) {
                        order.setCreatedAt(timestamp.toLocalDateTime());
                    }
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    @Override
    public List<OrderItem> findItemsByOrderId(long orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        try (var connection = com.shop.config.ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(FIND_ITEMS_BY_ORDER_ID)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(resultSet.getLong("id"));
                    item.setOrderId(resultSet.getLong("order_id"));
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
}
