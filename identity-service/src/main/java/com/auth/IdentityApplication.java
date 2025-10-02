package com.auth;

import com.auth.domains.User;
import com.auth.dtos.UserDto;
import com.auth.repos.UserRepository;
import com.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class IdentityApplication implements CommandLineRunner {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(IdentityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // loop over users and update dummy hashed password -> 123

        for (User user : userRepository.findAll()) {
            String rawPassword = "123";

            if (user.getPassword() == null || !user.getPassword().startsWith("$2")) {
                user.setPassword(passwordEncoder.encode(rawPassword));
                UserDto updated = userService.save(user);

                System.out.printf("Updated password for user: %s (id=%d)%n",
                        updated.getUsername(), updated.getUserId());
            }
        }
    }
}
