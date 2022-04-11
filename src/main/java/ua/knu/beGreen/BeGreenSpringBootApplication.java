package ua.knu.beGreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class BeGreenSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeGreenSpringBootApplication.class, args);
    }
}
