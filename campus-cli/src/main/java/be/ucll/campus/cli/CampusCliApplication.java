package be.ucll.campus.cli;

import be.ucll.campus.cli.client.CampusApiClient;
import be.ucll.campus.cli.model.Campus;
import be.ucll.campus.cli.model.Reservation;
import be.ucll.campus.cli.model.Room;
import be.ucll.campus.cli.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
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

                case 2 -> {
                    System.out.println("RESERVATIE MAKEN");
                    System.out.println("-----------------");

                    long userId;

                    while (true) {
                        System.out.print("Gebruikers-ID: ");

                        try {
                            userId = Long.parseLong(scanner.nextLine());

                            if (userId > 0) {
                                break;
                            }

                            System.out.println("Het gebruikers-ID moet groter zijn dan 0.");
                        } catch (NumberFormatException exception) {
                            System.out.println("Ongeldige invoer. Voer een geheel getal in.");
                        }
                    }

                    LocalDateTime availableFrom;

                    while (true) {
                        System.out.print("Begintijd (yyyy-MM-ddTHH:mm): ");

                        try {
                            availableFrom = LocalDateTime.parse(scanner.nextLine());
                            break;
                        } catch (DateTimeParseException exception) {
                            System.out.println("Ongeldige datum en tijd. Gebruik het formaat yyyy-MM-ddTHH:mm.");
                        }
                    }

                    LocalDateTime availableUntil;

                    while (true) {
                        System.out.print("Eindtijd (yyyy-MM-ddTHH:mm): ");

                        try {
                            availableUntil = LocalDateTime.parse(scanner.nextLine());

                            if (availableUntil.isAfter(availableFrom)) {
                                break;
                            }

                            System.out.println("De eindtijd moet na de begintijd liggen.");
                        } catch (DateTimeParseException exception) {
                            System.out.println("Ongeldige datum en tijd. Gebruik het formaat yyyy-MM-ddTHH:mm.");
                        }
                    }

                    int minNumberOfSeats;

                    while (true) {
                        System.out.print("Minimumaantal plaatsen per lokaal: ");

                        try {
                            minNumberOfSeats = Integer.parseInt(scanner.nextLine());

                            if (minNumberOfSeats > 0) {
                                break;
                            }

                            System.out.println("Het minimumaantal plaatsen moet groter zijn dan 0.");
                        } catch (NumberFormatException exception) {
                            System.out.println("Ongeldige invoer. Voer een geheel getal in.");
                        }
                    }

                    try {
                        List<Campus> campuses = campusApiClient.getCampuses();

                        List<Room> availableRooms = new ArrayList<>();

                        for (Campus campus : campuses) {
                            List<Room> campusRooms = campusApiClient.getAvailableRooms(campus.getName(), availableFrom, availableUntil, minNumberOfSeats);

                            for (Room room : campusRooms) {
                                room.setCampusName(campus.getName());
                            }

                            availableRooms.addAll(campusRooms);
                        }

                        System.out.println();
                        System.out.println("BESCHIKBARE LOKALEN");
                        System.out.println("-------------------");

                        if (availableRooms.isEmpty()) {
                            System.out.println("Geen beschikbare lokalen gevonden.");
                        } else {
                            for (int i = 0; i < availableRooms.size(); i++) {
                                Room room = availableRooms.get(i);

                                System.out.println((i + 1) + ". " + room.getCampusName() + " - " + room.getName() + " - " + room.getNumberOfSeats() + " plaatsen");
                            }

                            List<Room> selectedRooms = new ArrayList<>();

                            while (true) {
                                System.out.print("Kies een lokaalnummer (0 om de selectie af te ronden): ");

                                int roomChoice;

                                try {
                                    roomChoice = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException exception) {
                                    System.out.println("Ongeldige invoer. Voer een geheel getal in.");
                                    continue;
                                }

                                if (roomChoice == 0) {
                                    break;
                                }

                                if (roomChoice < 1 || roomChoice > availableRooms.size()) {
                                    System.out.println("Ongeldige keuze. Kies een nummer uit de lijst.");
                                    continue;
                                }

                                Room selectedRoom = availableRooms.get(roomChoice - 1);

                                if (selectedRooms.contains(selectedRoom)) {
                                    System.out.println("Dit lokaal werd al geselecteerd.");
                                    continue;
                                }

                                selectedRooms.add(selectedRoom);

                                System.out.println(selectedRoom.getCampusName() + " - " + selectedRoom.getName() + " werd geselecteerd.");
                            }

                            if (selectedRooms.isEmpty()) {
                                System.out.println("Er werden geen lokalen geselecteerd.");
                            } else {
                                System.out.println();
                                System.out.println("GESELECTEERDE LOKALEN");
                                System.out.println("---------------------");

                                System.out.print("Commentaar (optioneel): ");
                                String comment = scanner.nextLine().trim();

                                Reservation reservation = new Reservation(availableFrom, availableUntil, comment);

                                Reservation createdReservation = campusApiClient.createReservation(userId, reservation);

                                for (Room room : selectedRooms) {
                                    campusApiClient.addRoomToReservation(userId, createdReservation.getId(), room.getId());
                                }

                                System.out.println();
                                System.out.println("Reservatie succesvol aangemaakt.");
                                System.out.println("Reservatie-ID: " + createdReservation.getId());
                            }
                        }
                    } catch (WebClientResponseException exception) {
                        System.out.println();
                        System.out.println("De API heeft de aanvraag geweigerd.");
                        System.out.println(exception.getResponseBodyAsString());
                    } catch (WebClientRequestException exception) {
                        System.out.println();
                        System.out.println("De Campus API is niet bereikbaar, controleer of campus-api actief is.");
                    }
                }
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
                System.out.println("Ongeldige datum. Gebruik het formaat yyyy-MM-dd.");
            }
        }
    }
}