package be.ucll.campus.cli;

import be.ucll.campus.cli.client.CampusApiClient;
import be.ucll.campus.cli.model.Campus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CampusCliApplication implements CommandLineRunner {

    private final CampusApiClient campusApiClient;

    public CampusCliApplication(CampusApiClient campusApiClient) {
        this.campusApiClient = campusApiClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(CampusCliApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<Campus> campuses = campusApiClient.getCampuses();

        System.out.println("Campussen:");

        for (Campus campus : campuses) {
            System.out.println("- " + campus.getName());
        }
    }
}