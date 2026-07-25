package be.ucll.campus.cli;

import be.ucll.campus.cli.client.CampusApiClient;
import be.ucll.campus.cli.model.Campus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
                    break;
                } catch (NumberFormatException exception) {
                    System.out.println("Ongeldige invoer, kies een nummer uit de keuzelijst.");
                }
            }

            switch (choice) {
                case 1 ->
                    System.out.println("Gebruiker aanmaken");
                case 2 ->
                    System.out.println("Reservatie maken");
                case 0 ->
                    runApplication = false;
                default ->
                    System.out.println("Ongeldige keuze.");
            }

            System.out.println();
        }

        System.out.println("Applicatie afgesloten");
    }
}