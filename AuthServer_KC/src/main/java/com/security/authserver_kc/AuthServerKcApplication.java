package com.security.authserver_kc;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServerKcApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerKcApplication.class, args);
    }

}
