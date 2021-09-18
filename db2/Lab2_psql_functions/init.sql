DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS provider CASCADE;
DROP TABLE IF EXISTS buyer CASCADE;
DROP TABLE IF EXISTS sale CASCADE;
DROP TABLE IF EXISTS supply CASCADE;

CREATE TABLE product (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    weight FLOAT NOT NULL CHECK(weight >= 0),
    manufacturer VARCHAR(64) NOT NULL DEFAULT 'China',
    comment VARCHAR(256)
);

CREATE TABLE provider (
    id SERIAL NOT NULL PRIMARY KEY,
    firstname VARCHAR(64) NOT NULL,
    lastname VARCHAR(64) NOT NULL,
    patronymic VARCHAR(64),
    phone_number VARCHAR(64) NOT NULL
);

CREATE TABLE buyer (
    id SERIAL NOT NULL PRIMARY KEY,
    firstname VARCHAR(64) NOT NULL,
    lastname VARCHAR(64) NOT NULL,
    patronymic VARCHAR(64),
    phone_number VARCHAR(64) NOT NULL
);

CREATE TABLE supply (
    id SERIAL NOT NULL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES product(id),
    provider_id INT NOT NULL REFERENCES provider(id),
    price INT NOT NULL CHECK(price >= 0),
    quantity INT NOT NULL CHECK(quantity > 0),
    supply_date DATE NOT NULL
);

CREATE TABLE sale (
    id SERIAL NOT NULL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES product(id),
    buyer_id INT NOT NULL REFERENCES buyer(id),
    price INT NOT NULL CHECK(price >= 0),
    quantity INT NOT NULL CHECK(quantity > 0),
    sale_date DATE NOT NULL
);

INSERT INTO product(name, weight, manufacturer, comment) VALUES
('Сфера огненная', 0.2, 'Кельталас Солнечный Скиталец', 'Жжется'),
('Ледяная скорбь', 10, 'Нерзул', 'Острожно! Имеет свойство поглощать душу владельца');

INSERT INTO provider(firstname, lastname, patronymic, phone_number) VALUES
('Кольтира', 'Ткач смерти', '(нет)', '88005553535'),
('Сильвана', 'Ветрокрылая', 'Алексеевна', '811122233344');

INSERT INTO buyer(firstname, lastname, patronymic, phone_number) VALUES
('Артас', 'Менетил', 'Третий', '111111111111'),
('Мурадин', 'Бронзобородый', 'Первый', '22222222222');

INSERT INTO supply(product_id, provider_id, price, quantity, supply_date) VALUES
(1, 1, 450, 15, '2018-02-15 14:24:30'),
(2, 2, 15000, 1, '2017-12-30 18:12:50'),
(1, 2, 8970, 20, '2017-07-13 18:12:50'),
(1, 1, 4869, 500, '2017-08-13 18:12:50'),
(2, 1, 800, 8, '2017-11-30 18:12:50'),
(1, 1, 15000, 7, '2017-10-30 18:12:50');

INSERT INTO sale(product_id, buyer_id, price, quantity, sale_date) VALUES
(2, 1, 6660, 1, '2018-02-16 14:24:30'),
(1, 2, 14, 3, '2018-02-16 14:24:30'),
(2, 2, 15, 4, '2018-02-16 14:24:30'),
(1, 1, 16, 5, '2018-02-16 14:24:30'),
(2, 1, 17, 6, '2018-02-16 14:24:30'),
(2, 1, 18, 20, '2017-09-20 11:38:00');
