DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS campus;

CREATE TABLE campus
(
    name VARCHAR(255) PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    number_of_parking_spaces INT NOT NULL
);

CREATE TABLE app_user
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE room
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    room_type VARCHAR(255) NOT NULL,
    number_of_seats INT NOT NULL,
    floor INT NOT NULL,
    campus_name VARCHAR(255) NOT NULL,

    CONSTRAINT fk_room_campus
        FOREIGN KEY (campus_name)
        REFERENCES campus(name),

    CONSTRAINT uq_room_name_per_campus
        UNIQUE (campus_name, name)
)