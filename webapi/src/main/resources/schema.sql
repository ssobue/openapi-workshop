-- Create product table
-- DROP TABLE IF EXISTS products;
CREATE TABLE IF NOT EXISTS products
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Product ID',
    name     VARCHAR(1000)  NOT NULL COMMENT 'Product Name',
    currency CHAR(3)        NOT NULL COMMENT 'Currency',
    price    DECIMAL(10, 2) NOT NULL COMMENT 'Product Price',
    stock    INT            NOT NULL COMMENT 'Product stock quantity'
) COMMENT = 'Product';

-- Create order table
-- DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Order ID',
    customer_name VARCHAR(1000)                      NOT NULL COMMENT 'Customer Name',
    order_date    DATETIME                           NOT NULL COMMENT 'Ordered Date',
    status        CHAR(1)                            NOT NULL COMMENT 'Order Status',
    updated_on    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated Date'
) COMMENT = 'Order';

-- Create order items table
-- DROP TABLE IF EXISTS order_items;
CREATE TABLE IF NOT EXISTS order_items
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Order Item ID',
    order_id   BIGINT         NOT NULL COMMENT 'Order ID',
    product_id BIGINT         NOT NULL COMMENT 'Product ID',
    quantity   INT            NOT NULL COMMENT 'Ordered quantity',
    price      DECIMAL(10, 2) NOT NULL COMMENT 'Price',
    currency   CHAR(3)        NOT NULL COMMENT 'Applied currency',
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE RESTRICT
) COMMENT = 'Order detail';

-- Create exchange rates table
-- DROP TABLE IF EXISTS exchange_rates;
CREATE TABLE IF NOT EXISTS exchange_rates
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Exchange Rate ID',
    currency_code CHAR(3)                            NOT NULL UNIQUE COMMENT 'Currency Code',
    exchange_rate DECIMAL(10, 6)                     NOT NULL COMMENT 'Exchange rate',
    updated_on    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated Date'
) COMMENT = 'Exnchange Rate';
