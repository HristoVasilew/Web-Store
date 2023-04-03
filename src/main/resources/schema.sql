CREATE TABLE IF NOT EXISTS Users(

    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,

    first_name VARCHAR(50) NOT NULL,

    last_name VARCHAR(50) NOT NULL,

    email VARCHAR(50) UNIQUE NOT NULL,

    password VARCHAR(60) NOT NULL,

    date_added TIMESTAMP NOT NULL,

    type CHAR(1) NOT NULL

);

CREATE TABLE IF NOT EXISTS Products(

    barcode VARCHAR(36) UNIQUE PRIMARY KEY NOT NULL,

    date_updated TIMESTAMP NOT NULL,

    name VARCHAR(100) NOT NULL,

    price NUMERIC(7, 2) NOT NULL

);

CREATE TABLE IF NOT EXISTS Orders(

    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,

    user_id BIGINT,

    date TIMESTAMP NOT NULL,

    greeting VARCHAR(100) NOT NULL,

    total_price NUMERIC(10, 2) NOT NULL,

    CONSTRAINT fk_order_user
    FOREIGN KEY (user_id) REFERENCES users(id)

);


CREATE TABLE IF NOT EXISTS Orders_Products(

    order_id BIGINT NOT NULL,

    barcode CHARACTER VARYING(36) NOT NULL,

    CONSTRAINT pk_order_product PRIMARY KEY(order_id, barcode),

    CONSTRAINT fk_order_products
    FOREIGN KEY (order_id) REFERENCES orders (id),

    CONSTRAINT fk_product_orders
    FOREIGN KEY (barcode) REFERENCES products (barcode)

);

CREATE TABLE IF NOT EXISTS Addresses(

    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,

    user_id BIGINT,

    city VARCHAR(100) NOT NULL,

    street VARCHAR(100) NOT NULL,

    number SMALLINT,

    CONSTRAINT fk_user_address
    FOREIGN KEY (user_id) REFERENCES users(id)

);