-- Users
CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,

    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,

    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,

    role VARCHAR(50) NOT NULL,

    phone_number VARCHAR(20),

    is_active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);

-- Categories
CREATE TABLE categories(
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products
CREATE TABLE products(
    id BIGSERIAL PRIMARY KEY,

    category_id BIGINT,

    name VARCHAR(255) NOT NULL,
    description TEXT,

    sku VARCHAR(100) NOT NULL UNIQUE,

    price NUMERIC(12,2) NOT NULL,

    stock_quantity INTEGER NOT NULL DEFAULT 0,

    image_url TEXT,

    is_active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_products_category
                     FOREIGN KEY (category_id)
                     REFERENCES categories(id)
                     ON DELETE SET NULL
);

CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_sku ON products(sku);

-- Carts
CREATE TABLE carts(
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL UNIQUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_carts_users
                  FOREIGN KEY (user_id)
                  REFERENCES users(id)
                  ON DELETE CASCADE
);

-- Cart Items
CREATE TABLE cart_items(
    id BIGSERIAL PRIMARY KEY,

    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    quantity INTEGER NOT NULL CHECK (quantity > 0),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cart_items_product
                       FOREIGN KEY (product_id)
                       REFERENCES products(id)
                       ON DELETE CASCADE,

    CONSTRAINT fk_cart_items_cart
                       FOREIGN KEY (cart_id)
                       REFERENCES carts(id)
                       ON DELETE CASCADE
);

CREATE INDEX idx_cart_items_card_id ON cart_items(cart_id);
CREATE INDEX idx_cart_item_product_id ON cart_items(product_id);

-- Orders
CREATE TABLE orders(
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    order_status VARCHAR(50) NOT NULL,

    total_amount NUMERIC(12,2) NOT NULL,

    shipping_address_id BIGINT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_orders_user
                   FOREIGN KEY (user_id)
                   REFERENCES users(id)
);

CREATE INDEX idx_orders_user_id ON orders(user_id);

-- Order Items
CREATE TABLE order_items(
    id BIGSERIAL PRIMARY KEY,

    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    quantity INTEGER NOT NULL CHECK ( quantity > 0 ),
    unit_price NUMERIC(12,2) NOT NULL,
    total_price NUMERIC(12,2) NOT NULL,

    CONSTRAINT fk_order_items_order
                        FOREIGN KEY (order_id)
                        REFERENCES orders(id)
                        ON DELETE CASCADE,

    CONSTRAINT fk_order_items_product
                        FOREIGN KEY (product_id)
                        REFERENCES products(id)
                        ON DELETE CASCADE
);

CREATE INDEX idx_oder_items_order_id ON order_items(order_id);

-- Payment
CREATE TABLE payments(
    id BIGSERIAL NOT NULL,

    order_id BIGINT NOT NULL UNIQUE,

    payment_method VARCHAR(50) NOT NULL,
    payment_status VARCHAR(50) NOT NULL,

    transaction_id VARCHAR(255),

    amount NUMERIC(12,2) NOT NULL,

    paid_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT current_timestamp,

    CONSTRAINT fk_payments_order
                    FOREIGN KEY (order_id)
                    REFERENCES orders(id)
);

CREATE INDEX idx_payments_status ON payments(payment_status);

-- Inventory Logs

CREATE TABLE inventory_logs(
    id BIGSERIAL PRIMARY KEY,

    product_id BIGINT NOT NULL,

    change_type VARCHAR(50) NOT NULL,

    quantity_changed INTEGER NOT NULL,

    previous_quantity INTEGER NOT NULL,
    new_quantity INTEGER NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_inventory_logs_product_id
                           FOREIGN KEY (product_id)
                           REFERENCES products(id)
);

CREATE INDEX idx_inventory_logs_product_id ON inventory_logs(product_id);


