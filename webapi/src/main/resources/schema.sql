-- Create product table
-- DROP TABLE IF EXISTS products;
CREATE TABLE IF NOT EXISTS products
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(1000)  NOT NULL,
    currency CHAR(3)        NOT NULL,
    price    DECIMAL(10, 2) NOT NULL,
    stock    INT            NOT NULL
);

-- Create order table
-- DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(1000)                      NOT NULL,
    order_date    DATETIME                           NOT NULL,
    status        CHAR(1)                            NOT NULL,
    updated_on    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Create order items table
-- DROP TABLE IF EXISTS order_items;
CREATE TABLE IF NOT EXISTS order_items
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id   BIGINT         NOT NULL,
    product_id BIGINT         NOT NULL,
    quantity   INT            NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    currency   CHAR(3)        NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE RESTRICT
);

-- Create exchange rates table
-- DROP TABLE IF EXISTS exchange_rates;
CREATE TABLE IF NOT EXISTS exchange_rates
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    currency_code CHAR(3)                            NOT NULL UNIQUE,
    exchange_rate DECIMAL(10, 6)                     NOT NULL,
    updated_on    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);
