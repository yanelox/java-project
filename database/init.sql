DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(255),
    stock INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_products_category
        FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE cart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_cart_user
        FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_cart_product
        FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT uq_cart_user_product
        UNIQUE (user_id, product_id)
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'CREATED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(150) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_items_product
        FOREIGN KEY (product_id) REFERENCES products(id)
);

INSERT INTO categories (name, description) VALUES
('Laptops', 'Portable computers for work and study'),
('Smartphones', 'Modern phones for everyday use'),
('Accessories', 'Useful additions for your devices');

INSERT INTO products (category_id, name, description, price, image_url, stock) VALUES
(1, 'Lenovo IdeaPad 5', '15-inch laptop with Ryzen processor and SSD.', 799.99, 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853', 8),
(1, 'Dell Inspiron 14', 'Compact laptop for office tasks and browsing.', 699.00, 'https://images.unsplash.com/photo-1541807084-5c52b6b3adef', 6),
(2, 'Samsung Galaxy A55', 'Balanced smartphone with AMOLED display.', 449.50, 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9', 15),
(2, 'iPhone 13', 'Reliable Apple smartphone with strong camera.', 799.00, 'https://images.unsplash.com/photo-1510557880182-3d4d3cba35a5', 7),
(3, 'Wireless Mouse', 'Ergonomic mouse with silent clicks.', 29.90, 'https://images.unsplash.com/photo-1527814050087-3793815479db', 25),
(3, 'USB-C Hub', 'Multiport adapter with HDMI and USB 3.0.', 49.00, 'https://images.unsplash.com/photo-1587033411391-5d9e51cce126', 18);
