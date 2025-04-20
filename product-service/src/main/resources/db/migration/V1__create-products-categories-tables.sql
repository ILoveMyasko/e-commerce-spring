CREATE TABLE categories
(
    category_id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name        VARCHAR(50) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (category_id)
);

CREATE TABLE products
(
    product_id  BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name        VARCHAR(50)                             NOT NULL,
    description text,
    price       DECIMAL(19, 2),
    is_active   BOOLEAN                                 NOT NULL,
    weight_g    INTEGER,
    category_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (product_id)
);

ALTER TABLE categories
    ADD CONSTRAINT uc_categories_name UNIQUE (name);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (category_id);