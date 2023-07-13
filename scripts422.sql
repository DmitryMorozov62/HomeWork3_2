CREATE TABLE car
(
    id    BIGSERIAL PRIMARY KEY,
    brand VARCHAR(15)             NOT NULL,
    model VARCHAR(31)             NOT NULL,
    price INT CHECK ( price > 0 ) NOT NULL
);

CREATE TABLE human
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(15)                NOT NULL,
    age            INT CHECK ( age > 16 )     NOT NULL,
    driver_license BOOLEAN DEFAULT true       NOT NULL,
    car_id         BIGINT REFERENCES car (id) NOT NULL
);

INSERT INTO car(brand, model, price)
VALUES ('BMW', 'X6', 10000000),
       ('Audi', 'Q6', 8000000);

INSERT INTO human(name, age, car_id)
VALUES ('Ivan', 20, 2),
       ('Petr', 30, 1);

DROP TABLE car,human;


