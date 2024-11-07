package cyr.tos.microauthentification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroauthentificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroauthentificationApplication.class, args);
        System.out.println("=========== Immo-authentification Application started ======= ");
    }

}
