package com.nagarro.assignment;

import com.nagarro.assignment.domain.model.Librarian;
import com.nagarro.assignment.domain.model.Role;
import com.nagarro.assignment.domain.model.repository.LibrarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class WebApplication {

    private final LibrarianRepository librarianRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(PasswordEncoder passwordEncoder,
                                          LibrarianRepository librarianRepository) {
        return args -> {
            // Optionally check if the admin user already exists to avoid duplicates
            if (librarianRepository.findByUsername("admin").isPresent()) {
                return;
            }

            // Create a new Role instance
            Role role = new Role();
            role.setName("ROLE_ADMIN");

            // Create and configure the Librarian
            Librarian librarian = new Librarian();
            librarian.setUsername("admin");
            librarian.setPassword(passwordEncoder.encode("Temp123"));
            librarian.setRoles(Set.of(role)); // Cascade persist will save the role

            librarianRepository.save(librarian);
        };
    }
}