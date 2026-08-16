package be.ucll.campus.cli.client;

import be.ucll.campus.cli.model.Campus;
import be.ucll.campus.cli.model.Reservation;
import be.ucll.campus.cli.model.Room;
import be.ucll.campus.cli.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Communiceerd met de Campus REST API via WebClient.
 *
 * De CLI heeft geen directe toegang tot de database, alle data wordt opgehaald en aangepast via HTTP requests naar campus-api.
 *
 * WebClient is reactief, maar deze command-line app werkt synchroon, daarom wordt block() gebruikt om te wachten op responses van de API.
 */
@Component
public class CampusApiClient {
    private final WebClient webClient;

    public CampusApiClient(WebClient.Builder webClientBuilder) {
        // Alle requests van de CLI worden doorgestuurd naar de REST API op poort 8080.
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public List<Campus> getCampuses() {
        return webClient
                .get()
                .uri("/campus")
                .retrieve()
                // Dit endpoint geeft meerdere campussen terug, dus een Flux wordt omgezet naar een List.
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
                // De response bevat één gebruiker, dus wordt gelezen als een Mono.
                .bodyToMono(User.class)
                .block();
    }

    public List<Room> getAvailableRooms(String campusName, LocalDateTime availableFrom, LocalDateTime availableUntil, int minNumberOfSeats) {
        return webClient
                .get()
                // uriBuilder wordt gebruikt om de filters als queryparameters aan de URL toe te voegen.
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
                // De PUT moet alleen slagen, de CLI heeft geen response body nodig.
                .toBodilessEntity()
                .block();
    }
}
