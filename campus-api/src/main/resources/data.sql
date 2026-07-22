INSERT INTO campus (name, address, number_of_parking_spaces)
VALUES ('TestCampus1', 'TestAddress1', 10);

INSERT INTO campus (name, address, number_of_parking_spaces)
VALUES ('TestCampus2', 'TestAddress2', 20);

INSERT INTO campus (name, address, number_of_parking_spaces)
VALUES ('TestCampus3', 'TestAddress3', 30);


INSERT INTO room (name, room_type, number_of_seats, floor, campus_name)
VALUES ('TestRoom1', 'TestType1', 10, 1, 'TestCampus1');

INSERT INTO room (name, room_type, number_of_seats, floor, campus_name)
VALUES ('TestRoomSameName', 'TestType2', 20, 2, 'TestCampus1');

INSERT INTO room (name, room_type, number_of_seats, floor, campus_name)
VALUES ('TestRoomSameName', 'TestType1', 30, 1, 'TestCampus2');

INSERT INTO app_user (last_name, first_name, birth_date, email)
VALUES ('TestLastName1', 'TestFirstName1', '2001-01-01', 'testuser1@email.com');

INSERT INTO app_user (last_name, first_name, birth_date, email)
VALUES ('TestLastName2', 'TestFirstName2', '2002-02-02', 'testuser2@email.com');

INSERT INTO app_user (last_name, first_name, birth_date, email)
VALUES ('Doe', 'John', '2003-03-03', 'john.doe@email.com');