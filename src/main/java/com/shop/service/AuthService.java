package com.shop.service;

import com.shop.dao.UserDao;
import com.shop.dao.impl.JdbcUserDao;
import com.shop.model.Role;
import com.shop.model.User;
import com.shop.util.PasswordUtil;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
    private final UserDao userDao;

    public AuthService() {
        this(new JdbcUserDao());
    }

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User register(String name, String email, String password) throws SQLException {
        validateRegistration(name, email, password);
        if (userDao.existsByEmail(email.trim().toLowerCase())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        User user = new User();
        user.setName(name.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setPasswordHash(PasswordUtil.hash(password));
        user.setRole(Role.USER);
        return userDao.create(user);
    }

    public Optional<User> login(String email, String password) throws SQLException {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Email and password are required.");
        }

        Optional<User> user = userDao.findByEmail(email.trim().toLowerCase());
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return PasswordUtil.matches(password, user.get().getPasswordHash()) ? user : Optional.empty();
    }

    private void validateRegistration(String name, String email, String password) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }
    }
}
