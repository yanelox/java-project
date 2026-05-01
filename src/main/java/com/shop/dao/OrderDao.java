package com.shop.dao;

import com.shop.model.Order;
import com.shop.model.OrderItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    long createOrder(Connection connection, Order order) throws SQLException;

    void createOrderItems(Connection connection, long orderId, List<OrderItem> items) throws SQLException;

    List<Order> findByUserId(long userId) throws SQLException;

    List<OrderItem> findItemsByOrderId(long orderId) throws SQLException;
}
