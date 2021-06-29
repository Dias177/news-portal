package com.epam.tc.news;

import com.epam.tc.news.entity.Role;
import com.epam.tc.news.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
@AllArgsConstructor
public class NewsApplication implements CommandLineRunner {

    private static final Long ROLE_USER_ID = 1L;
    private static final Long ROLE_ADMIN_ID = 2L;
    private static final String ROLE_USER_NAME = "ROLE_USER";
    private static final String ROLE_ADMIN_NAME = "ROLE_ADMIN";

    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        Optional<Role> foundRoleUser = roleRepository.findById(ROLE_USER_ID);
        Optional<Role> foundRoleAdmin = roleRepository.findById(ROLE_ADMIN_ID);
        if (foundRoleUser.isEmpty()) {
            var roleUser = new Role(ROLE_USER_ID, ROLE_USER_NAME);
            roleRepository.save(roleUser);
        }
        if (foundRoleAdmin.isEmpty()) {
            var roleAdmin = new Role(ROLE_ADMIN_ID, ROLE_ADMIN_NAME);
            roleRepository.save(roleAdmin);
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);
    }

}
