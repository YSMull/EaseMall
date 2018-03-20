DROP TABLE ease_goods_pic IF EXISTS;
DROP TABLE ease_purchase_record IF EXISTS;
DROP TABLE ease_cart IF EXISTS;
DROP TABLE ease_goods_info IF EXISTS;
DROP TABLE ease_shop_user IF EXISTS;
DROP TABLE ease_test IF EXISTS;

CREATE TABLE ease_shop_user (
  id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  username VARCHAR(128) NOT NULL,
  password VARCHAR(128) NOT NULL,
  role INT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE ease_goods_info (
  id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 100, INCREMENT BY 1),
  name VARCHAR(128) NOT NULL,
  price FLOAT NOT NULL,
  description VARCHAR(200) NOT NULL,
  detail CLOB(4000),
  pic_url VARCHAR(255) NOT NULL,
  publisher BIGINT DEFAULT 1 NOT NULL,
  status INT DEFAULT 1 NOT NULL ,
  PRIMARY KEY (id)
);

CREATE TABLE ease_goods_pic (
  id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 100, INCREMENT BY 1),
  uuid VARCHAR(100) NOT NULL,
  name VARCHAR(128) NOT NULL,
  pic BLOB,
  PRIMARY KEY (id)
);

CREATE TABLE ease_purchase_record (
  id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1000, INCREMENT BY 1),
  user_id BIGINT NOT NULL,
  goods_id BIGINT NOT NULL,
  snap_goods_name VARCHAR(128) NOT NULL,
  snap_price FLOAT NOT NULL,
  snap_description VARCHAR(200) NOT NULL,
  snap_detail VARCHAR(2000) DEFAULT '' NOT NULL,
  snap_pic_url VARCHAR(255) NOT NULL,
  amount INT NOT NULL,
  purchase_time TIMESTAMP DEFAULT current_timestamp NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (goods_id) REFERENCES ease_goods_info(id)
);

CREATE TABLE ease_cart (
  id BIGINT UNIQUE NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1000, INCREMENT BY 1),
  user_id BIGINT NOT NULL,
  goods_id BIGINT NOT NULL,
  amount int NOT NULL,
  status int DEFAULT 1 NOT NULL ,
  create_time TIMESTAMP DEFAULT current_timestamp NOT NULL,
  update_time TIMESTAMP DEFAULT current_timestamp NOT NULL,
  FOREIGN KEY (user_id) REFERENCES ease_shop_user(id),
  FOREIGN KEY (goods_id) REFERENCES ease_goods_info(id)

);


-- 不加of会循环trigger直至溢出
-- Two triggers on same table cause "ERROR 54038: Maximum depth of nested triggers was exceeded."
-- https://stackoverflow.com/questions/21196247/derby-trigger-on-update-how-to-update-a-timestamp
CREATE TRIGGER ease_cart_update
  AFTER UPDATE OF status,amount ON ease_cart
  REFERENCING OLD ROW AS oldRow
  FOR EACH ROW
  UPDATE ease_cart
  SET update_time = current_timestamp
  WHERE id = oldRow.id;


--
-- LOCK TABLE ease_cart IN SHARE MODE;
-- LOCK TABLE ease_cart IN EXCLUSIVE MODE;


-- for test
CREATE TABLE ease_test (
  id BIGINT UNIQUE NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1000, INCREMENT BY 1),
  name VARCHAR(200)
);