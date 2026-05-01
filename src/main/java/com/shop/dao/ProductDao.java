package com.shop.dao;

import com.shop.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> findAll() throws SQLException;

    List<Product> findByCategoryId(long categoryId) throws SQLException;

    Optional<Product> findById(long id) throws SQLException;
}
