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