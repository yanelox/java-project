package com.shop.dao.impl;

import com.shop.config.ConnectionFactory;
import com.shop.dao.CategoryDao;
import com.shop.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCategoryDao implements CategoryDao {
    private static final String FIND_ALL = "SELECT * FROM categories ORDER BY name";
    private static final String FIND_BY_ID = "SELECT * FROM categories WHERE id = ?";

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        try (var connection = ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                categories.add(map(resultSet));
            }
        }
        return categories;
    }

    @Override
    public Optional<Category> findById(long id) throws SQLException {
        try (var connection = ConnectionFactory.getConnection();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(map(resultSet)) : Optional.empty();
            }
        }
    }

    private Category map(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description")
        );
    }
}
