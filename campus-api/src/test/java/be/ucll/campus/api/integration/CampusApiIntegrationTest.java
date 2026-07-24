package be.ucll.campus.api.integration;

import be.ucll.campus.api.model.Campus;
import be.ucll.campus.api.repository.CampusRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql({"classpath:schema.sql", "classpath:test.sql"})
public class CampusApiIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CampusRepository campusRepository;

    @AfterEach
    public void cleanUp() {
        campusRepository.removeAllCampuses();
    }

    @Test
    public void givenNoCampusNamedTestCampus4_WhenInvokingPostCampus_ThenCampusIsSaved() {
        webTestClient.post()
                .uri("/campus")
                .header("Content-Type", "application/json")
                .bodyValue("{\"name\":\"TestCampus4\",\"address\":\"TestAddress4\",\"numberOfParkingSpaces\":40}")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .json("{\"name\":\"TestCampus4\",\"address\":\"TestAddress4\",\"numberOfParkingSpaces\":40}");

        Campus savedCampus = campusRepository.findCampusByName("TestCampus4").orElseThrow();

        Assertions.assertEquals("TestCampus4", savedCampus.getName());
        Assertions.assertEquals("TestAddress4", savedCampus.getAddress());
        Assertions.assertEquals(40, savedCampus.getNumberOfParkingSpaces());
    }
}
