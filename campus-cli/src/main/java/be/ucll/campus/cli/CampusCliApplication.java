package be.ucll.campus.cli;

import be.ucll.campus.cli.client.CampusApiClient;
import be.ucll.campus.cli.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@SpringBootApplication
public class CampusCliApplication implements CommandLineRunner {

    private final CampusApiClient campusApiClient;
    private final Scanner scanner = new Scanner(System.in);

    public CampusCliApplication(CampusApiClient campusApiClient) {
        this.campusApiClient = campusApiClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(CampusCliApplication.class, args);
    }

    @Override
    public void run(String... args) {
        boolean runApplication = true;

        while (runApplication) {
            System.out.println("CAMPUS COMMANDLINE-APP");
            System.out.println("----------------------");
            System.out.println("1. Gebruiker aanmaken");
            System.out.println("2. Reservatie maken");
            System.out.println("0. Afsluiten");
            System.out.println("----------------------");

            int choice;

            while (true) {
                System.out.print("Keuze: ");
                try {
                    choice = Integer.parseInt(scanner.nextLine());

                    if (choice == 0 || choice == 1 || choice == 2) {
                        break;
                    }

                    System.out.println("Ongeldige keuze, kies een nummer uit de keuzelijst");
                } catch (NumberFormatException exception) {
                    System.out.println("Ongeldige invoer, kies een nummer uit de keuzelijst.");
                }
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("GEBRUIKER AANMAKEN");
                    System.out.println("------------------");

                    String firstName = readRequiredText("Voornaam: ");
                    String lastName = readRequiredText("Achternaam: ");
                    LocalDate birthDate = readDate("Geboortedatum (yyyy-MM-dd): ");
                    String email = readRequiredText("E-mailadres: ");

                    User user = new User(lastName, firstName, birthDate, email);

                    try {
                        User createdUser = campusApiClient.createUser(user);

                        System.out.println();
                        System.out.println("Gebruiker succesvol aangemaakt.");
                        System.out.println("Gebruikers-ID: " + createdUser.getId());
                        System.out.println("Naam: " + createdUser.getFirstName() + " " + createdUser.getLastName());
                    } catch (WebClientResponseException exception) {
                        System.out.println();
                        System.out.println("De API heeft de aanvraag geweigerd.");
                        System.out.println(exception.getResponseBodyAsString());
                    } catch (WebClientRequestException exception) {
                        System.out.println();
                        System.out.println("De Campus API is niet bereikbaar, controleer of campus-api actief is.");
                    }
                }

                case 2 ->
                    System.out.println("Reservatie maken");
                case 0 ->
                    runApplication = false;
            }

            System.out.println();
        }

        System.out.println("Applicatie afgesloten");
    }

    private String readRequiredText(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            if (!input.isBlank()) {
                return input;
            }

            System.out.println("Dit veld mag niet leeg zijn.");
        }
    }

    private LocalDate readDate(String message) {
        while (true) {
            System.out.print(message);

            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException exception) {
                System.out.println(
                        "Ongeldige datum. Gebruik het formaat yyyy-MM-dd."
                );
            }
        }
    }
}