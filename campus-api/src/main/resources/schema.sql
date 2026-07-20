DROP TABLE IF EXISTS campus;

CREATE TABLE campus
(
    name VARCHAR(255) PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    number_of_parking_spaces INT NOT NULL
);