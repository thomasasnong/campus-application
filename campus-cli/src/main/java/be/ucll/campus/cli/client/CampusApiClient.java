package be.ucll.campus.cli.client;

import be.ucll.campus.cli.model.Campus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
}
