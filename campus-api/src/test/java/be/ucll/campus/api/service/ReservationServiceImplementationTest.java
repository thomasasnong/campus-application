package be.ucll.campus.api.service;

import be.ucll.campus.api.error.ReservationCannotBeInPastException;
import be.ucll.campus.api.error.ReservationStartMustBeBeforeEndException;
import be.ucll.campus.api.error.RoomAlreadyInReservationException;
import be.ucll.campus.api.error.RoomNotAvailableException;
import be.ucll.campus.api.model.Reservation;
import be.ucll.campus.api.model.Room;
import be.ucll.campus.api.model.User;
import be.ucll.campus.api.repository.ReservationRepository;
import be.ucll.campus.api.repository.RoomRepository;
import be.ucll.campus.api.repository.UserRepository;
import be.ucll.campus.api.service.ReservationService;
import be.ucll.campus.api.service.ReservationServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceImplementationTest {

    private static final long USER_ID = 1;
    private static final long RESERVATION_ID = 1;
    private static final long ROOM_ID = 1;

    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;

    private ReservationService reservationService;

    @BeforeEach
    void setup() {
        reservationRepository = mock(ReservationRepository.class);
        roomRepository = mock(RoomRepository.class);
        userRepository = mock(UserRepository.class);
        reservationService = new ReservationServiceImplementation(reservationRepository, userRepository, roomRepository);
    }

    @Test
    void addValidReservationSavesReservation() {
        User user = new User("Doe", "John", LocalDate.of(2000, 1, 1), "john.doe@email.com");

        Reservation reservation = new Reservation(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(4), "Test reservation");

        when(userRepository.findUserById(USER_ID)).thenReturn(Optional.of(user));

        when(reservationRepository.saveReservation(reservation)).thenReturn(reservation);

        Reservation result = reservationService.addReservation(USER_ID, reservation);

        assertSame(reservation, result);
        assertSame(user, result.getUser());

        verify(reservationRepository).saveReservation(reservation);
    }

    @Test
    void addRoomToNonOverlappingReservationSucceeds() {
        User user = mock(User.class);
        Reservation reservation = mock(Reservation.class);
        Reservation existingReservation = mock(Reservation.class);
        Room room = mock(Room.class);

        when(userRepository.findUserById(USER_ID)).thenReturn(Optional.of(user));

        when(reservationRepository.findReservationByIdAndUser(RESERVATION_ID, user)).thenReturn(Optional.of(reservation));

        when(roomRepository.findRoomById(ROOM_ID)).thenReturn(Optional.of(room));

        when(reservation.getRooms()).thenReturn(List.of());

        when(reservation.getId()).thenReturn(RESERVATION_ID);

        when(reservation.getStartTime()).thenReturn(LocalDateTime.now().plusHours(2));

        when(reservation.getEndTime()).thenReturn(LocalDateTime.now().plusHours(4));

        when(existingReservation.getId()).thenReturn(2L);

        when(existingReservation.getStartTime()).thenReturn(LocalDateTime.now().plusHours(6));

        when(existingReservation.getEndTime()).thenReturn(LocalDateTime.now().plusHours(8));

        when(reservationRepository.getReservationsByRoom(room)).thenReturn(List.of(existingReservation));

        when(reservationRepository.saveReservation(reservation)).thenReturn(reservation);

        Reservation result = reservationService.addRoomToReservation(USER_ID, RESERVATION_ID, ROOM_ID);

        assertSame(reservation, result);

        verify(reservation).addRoom(room);

        verify(reservationRepository).saveReservation(reservation);
    }

    @Test
    void addReservationWithStartAfterEndThrowsException() {
        User user = mock(User.class);

        Reservation reservation = new Reservation(LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(2), "Invalid reservation");

        when(userRepository.findUserById(USER_ID)).thenReturn(Optional.of(user));

        assertThrows(ReservationStartMustBeBeforeEndException.class, () -> {
            reservationService.addReservation(USER_ID, reservation);
        });
    }

    @Test
    void addReservationInPastThrowsException() {
        User user = mock(User.class);

        Reservation reservation = new Reservation(LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1), "Past reservation");

        when(userRepository.findUserById(USER_ID)).thenReturn(Optional.of(user));

        assertThrows(ReservationCannotBeInPastException.class, () -> {
            reservationService.addReservation(USER_ID, reservation);
        });
    }

    @Test
    void addDuplicateRoomsThrowsException() {
        User user = mock(User.class);
        Reservation reservation = mock(Reservation.class);
        Room room = mock(Room.class);

        when(userRepository.findUserById(USER_ID)).thenReturn(Optional.of(user));

        when(reservationRepository.findReservationByIdAndUser(RESERVATION_ID, user)).thenReturn(Optional.of(reservation));

        when(roomRepository.findRoomById(ROOM_ID)).thenReturn(Optional.of(room));

        when(room.getId()).thenReturn(ROOM_ID);

        when(reservation.getId()).thenReturn(RESERVATION_ID);

        when(reservation.getRooms()).thenReturn(List.of(room));

        assertThrows(RoomAlreadyInReservationException.class, () -> {
            reservationService.addRoomToReservation(USER_ID, RESERVATION_ID, ROOM_ID);
        });
    }

    @Test
    void addUnavailableRoomThrowsException() {
        User user = mock(User.class);
        Reservation reservation = mock(Reservation.class);
        Room room = mock(Room.class);
        Reservation existingReservation = mock(Reservation.class);

        when(userRepository.findUserById(USER_ID)).thenReturn(Optional.of(user));

        when(reservationRepository.findReservationByIdAndUser(RESERVATION_ID, user)).thenReturn(Optional.of(reservation));

        when(roomRepository.findRoomById(ROOM_ID)).thenReturn(Optional.of(room));

        when(reservation.getRooms()).thenReturn(List.of());

        when(reservation.getId()).thenReturn(RESERVATION_ID);

        when(reservation.getStartTime()).thenReturn(LocalDateTime.now().plusHours(2));

        when(reservation.getEndTime()).thenReturn(LocalDateTime.now().plusHours(4));

        when(existingReservation.getStartTime()).thenReturn(LocalDateTime.now().plusHours(1));

        when(existingReservation.getEndTime()).thenReturn(LocalDateTime.now().plusHours(3));

        when(reservationRepository.getReservationsByRoom(room)).thenReturn(List.of(existingReservation));

        assertThrows(RoomNotAvailableException.class, () -> {
            reservationService.addRoomToReservation(USER_ID, RESERVATION_ID, ROOM_ID);
        });
    }
}
