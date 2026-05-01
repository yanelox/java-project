package com.shop.service;

import com.shop.config.ConnectionFactory;
import com.shop.dao.CartDao;
import com.shop.dao.OrderDao;
import com.shop.dao.impl.JdbcCartDao;
import com.shop.dao.impl.JdbcOrderDao;
import com.shop.model.CartItem;
import com.shop.model.Order;
import com.shop.model.OrderItem;
import com.shop.model.OrderStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final CartDao cartDao;
    private final OrderDao orderDao;
    private final CartService cartService;

    public OrderService() {
        this(new JdbcCartDao(), new JdbcOrderDao());
    }

    public OrderService(CartDao cartDao, OrderDao orderDao) {
        this.cartDao = cartDao;
        this.orderDao = orderDao;
        this.cartService = new CartService(cartDao, new com.shop.dao.impl.JdbcProductDao());
    }

    public void placeOrder(long userId) throws SQLException {
        List<CartItem> cartItems = cartDao.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty.");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CREATED);
        order.setTotalAmount(cartService.calculateTotal(cartItems));

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem item = new OrderItem();
            item.setProductId(cartItem.getProductId());
            item.setProductName(cartItem.getProductName());
            item.setPrice(cartItem.getPrice());
            item.setQuantity(cartItem.getQuantity());
            orderItems.add(item);
        }

        try (Connection connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try {
                long orderId = orderDao.createOrder(connection, order);
                orderDao.createOrderItems(connection, orderId, orderItems);
                try (var clearStatement = connection.prepareStatement("DELETE FROM cart WHERE user_id = ?")) {
                    clearStatement.setLong(1, userId);
                    clearStatement.executeUpdate();
                }
                connection.commit();
            } catch (Exception exception) {
                connection.rollback();
                if (exception instanceof SQLException) {
                    throw (SQLException) exception;
                }
                throw new SQLException("Failed to place order", exception);
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public List<Order> getOrders(long userId) throws SQLException {
        List<Order> orders = orderDao.findByUserId(userId);
        for (Order order : orders) {
            order.setItems(orderDao.findItemsByOrderId(order.getId()));
        }
        return orders;
    }
}
