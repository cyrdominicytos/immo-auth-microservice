package cyr.tos.microauthentification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroauthentificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroauthentificationApplication.class, args);
        System.out.println("=========== Microauthentification Application started ======= ");
    }

}
