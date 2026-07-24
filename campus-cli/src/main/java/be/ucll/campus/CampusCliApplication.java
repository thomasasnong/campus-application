package be.ucll.campus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CampusCliApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusCliApplication.class, args);
        System.out.println("CampusCliApplication started");
    }

}
