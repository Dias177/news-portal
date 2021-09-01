package com.epam.tc.news;

import com.epam.tc.news.entity.News;
import com.epam.tc.news.entity.Role;
import com.epam.tc.news.entity.User;
import com.epam.tc.news.repository.RoleRepository;
import com.epam.tc.news.service.NewsService;
import com.epam.tc.news.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
@AllArgsConstructor
public class NewsApplication implements CommandLineRunner {

    private static final Long ROLE_USER_ID = 1L;
    private static final Long ROLE_ADMIN_ID = 2L;
    private static final String ROLE_USER_NAME = "ROLE_USER";
    private static final String ROLE_ADMIN_NAME = "ROLE_ADMIN";

    private RoleRepository roleRepository;
    private UserService userService;
    private NewsService newsService;

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

        var firstName = "Frank";
        var lastName = "Underwood";
        var email = "frank.underwood@gmail.org";
        var password = "123QWer@";
        var mobileNumber = "+77017654321";
        var birthday = new Date();
        var role = new Role(ROLE_USER_ID, ROLE_USER_NAME);

        var user = User.builder().email(email).password(password).firstName(firstName)
                .lastName(lastName).mobileNumber(mobileNumber).birthday(birthday).role(role).build();
        userService.createUser(user);


        var admin = User.builder().email("admin@news.com").password(password).firstName("Admin")
                .lastName("Adminov").mobileNumber(mobileNumber).birthday(birthday)
                .role(new Role(ROLE_ADMIN_ID, ROLE_ADMIN_NAME)).build();
        userService.createUser(admin);

        var title = "Some title";
        var brief = "Some brief";
        var content = "Some content";
        var n = new News();
        n.setUser(user);
        n.setContent(content);
        n.setBrief(brief);
        n.setTitle(title);

        newsService.createNews(n);

    }

    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);
    }

}
