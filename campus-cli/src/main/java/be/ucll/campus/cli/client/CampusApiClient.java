package be.ucll.campus.cli.client;

import be.ucll.campus.cli.model.Campus;
import be.ucll.campus.cli.model.Reservation;
import be.ucll.campus.cli.model.Room;
import be.ucll.campus.cli.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CampusApiClient {
    private final WebClient webClient;

    public CampusApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public List<Campus> getCampuses() {
        return webClient
                .get()
                .uri("/campus")
                .retrieve()
                .bodyToFlux(Campus.class)
                .collectList()
                .block();
    }

    public User createUser(User user) {
        return webClient
                .post()
                .uri("/user")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public List<Room> getAvailableRooms(String campusName, LocalDateTime availableFrom, LocalDateTime availableUntil, int minNumberOfSeats) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/campus/{campusName}/rooms")
                        .queryParam("availableFrom", availableFrom)
                        .queryParam("availableUntil", availableUntil)
                        .queryParam("minNumberOfSeats", minNumberOfSeats)
                        .build(campusName)
                )
                .retrieve()
                .bodyToFlux(Room.class)
                .collectList()
                .block();
    }

    public Reservation createReservation(long userId, Reservation reservation) {
        return webClient
                .post()
                .uri("/user/{userId}/reservations", userId)
                .bodyValue(reservation)
                .retrieve()
                .bodyToMono(Reservation.class)
                .block();
    }

    public void addRoomToReservation(long userId, long reservationId, long roomId) {
        webClient
                .put()
                .uri("/user/{userId}/reservations/{reservationId}/rooms/{roomId}", userId, reservationId, roomId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
