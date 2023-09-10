CREATE TABLE cars
(
    id   int PRIMARY KEY,
    name varchar NOT NULL,
    cost int     NOT NULL
);

CREATE TABLE drivers
(
    id          int PRIMARY KEY,
    driver_name varchar NOT NULL,
    car_id      int REFERENCES cars (id)
);