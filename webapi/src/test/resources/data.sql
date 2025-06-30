-- Insert products
INSERT INTO products (name, price, currency, stock)
VALUES ('Laptop', 1200.00, 'USD', 50),
       ('Smartphone', 800.00, 'USD', 100),
       ('Headphones', 150.00, 'USD', 200),
       ('Keyboard', 70.00, 'USD', 150),
       ('Mouse', 50.00, 'USD', 300);

-- Insert orders
INSERT INTO orders (customer_name, order_date, status)
VALUES ('Alice Johnson', '2024-11-01 10:00:00', '0'),
       ('Bob Smith', '2024-11-02 15:30:00', '9'),
       ('Carol Taylor', '2024-11-03 12:45:00', '0');

-- Insert order items
INSERT INTO order_items (order_id, product_id, quantity, price, currency)
VALUES (1, 1, 1, 1200.00, 'USD'), -- Alice ordered 1 Laptop
       (1, 3, 2, 150.00, 'USD'),  -- Alice ordered 2 Headphones
       (2, 2, 1, 800.00, 'USD'),  -- Bob ordered 1 Smartphone
       (2, 5, 1, 50.00, 'USD'),   -- Bob ordered 1 Mouse
       (3, 4, 1, 70.00, 'USD'),   -- Carol ordered 1 Keyboard
       (3, 3, 1, 150.00, 'USD');
-- Carol ordered 1 Headphones

-- Insert exchange rates
INSERT INTO exchange_rates (currency_code, exchange_rate)
VALUES ('USD', 1.000000),
       ('EUR', 0.850000),
       ('JPY', 110.000000);
