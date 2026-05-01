package com.shop.dao.impl;

import com.shop.config.ConnectionFactory;
import com.shop.dao.ProductDao;
import com.shop.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcProductDao implements ProductDao {
    private static final String BASE_SELECT =
            "SELECT p.*, c.name AS category_name " +
            "FROM products p " +
            "JOIN categories c ON c.id = p.category_id";
    private static final String FIND_ALL = BASE_SELECT + " ORDER BY p.created_at DESC, p.id DESC";
    private static final String FIND_BY_CATEGORY = BASE_SELECT + " WHERE p.category_id = ? ORDER BY p.created_at DESC, p.id DESC";
    private static final String FIND_BY_ID = BASE_SELECT + " WHERE p.id = ?";

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (var connection = ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                products.add(map(resultSet));
            }
        }
        return products;
    }

    @Override
    public List<Product> findByCategoryId(long categoryId) throws SQLException {
        List<Product> products = new ArrayList<>();
        try (var connection = ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(FIND_BY_CATEGORY)) {
            statement.setLong(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(map(resultSet));
                }
            }
        }
        return products;
    }

    @Override
    public Optional<Product> findById(long id) throws SQLException {
        try (var connection = ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(map(resultSet)) : Optional.empty();
            }
        }
    }

    private Product map(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setCategoryId(resultSet.getLong("category_id"));
        product.setCategoryName(resultSet.getString("category_name"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setImageUrl(resultSet.getString("image_url"));
        product.setStock(resultSet.getInt("stock"));
        var timestamp = resultSet.getTimestamp("created_at");
        if (timestamp != null) {
            product.setCreatedAt(timestamp.toLocalDateTime());
        }
        return product;
    }
}
