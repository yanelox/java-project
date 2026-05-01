package com.shop.service;

import com.shop.dao.CategoryDao;
import com.shop.dao.ProductDao;
import com.shop.dao.impl.JdbcCategoryDao;
import com.shop.dao.impl.JdbcProductDao;
import com.shop.model.Category;
import com.shop.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductDao productDao;
    private final CategoryDao categoryDao;

    public ProductService() {
        this(new JdbcProductDao(), new JdbcCategoryDao());
    }

    public ProductService(ProductDao productDao, CategoryDao categoryDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }

    public List<Product> findAllProducts() throws SQLException {
        return productDao.findAll();
    }

    public List<Product> findProductsByCategory(long categoryId) throws SQLException {
        return productDao.findByCategoryId(categoryId);
    }

    public Optional<Product> findProductById(long productId) throws SQLException {
        return productDao.findById(productId);
    }

    public List<Category> findAllCategories() throws SQLException {
        return categoryDao.findAll();
    }
}
