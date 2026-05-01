package com.shop.dao;

import com.shop.model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id) throws SQLException;

    Optional<User> findByEmail(String email) throws SQLException;

    User create(User user) throws SQLException;

    boolean existsByEmail(String email) throws SQLException;
}
