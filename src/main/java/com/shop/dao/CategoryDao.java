package com.shop.dao;

import com.shop.model.Category;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    List<Category> findAll() throws SQLException;

    Optional<Category> findById(long id) throws SQLException;
}
