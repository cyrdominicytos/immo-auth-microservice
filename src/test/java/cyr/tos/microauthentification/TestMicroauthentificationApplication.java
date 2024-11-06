package cyr.tos.microauthentification;

import org.springframework.boot.SpringApplication;

public class TestMicroauthentificationApplication {

    public static void main(String[] args) {
        SpringApplication.from(MicroauthentificationApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
