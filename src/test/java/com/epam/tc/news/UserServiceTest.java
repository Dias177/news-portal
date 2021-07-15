package com.epam.tc.news;

import com.epam.tc.news.entity.News;
import com.epam.tc.news.entity.Role;
import com.epam.tc.news.entity.User;
import com.epam.tc.news.exception.UserNotFoundException;
import com.epam.tc.news.repository.UserRepository;
import com.epam.tc.news.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final Long USER_ROLE_ID = 1L;
    private static final String USER_ROLE_NAME = "USER";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void getAllUsersWhenExistTest() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("John");
        user1.setLastName("Smith");
        user1.setEmail("john.smith@gmail.com");
        user1.setMobileNumber("+77771234567");
        user1.setBirthday(new Date());
        user1.setPassword("asd!1jf23");

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Adam");
        user2.setLastName("Wood");
        user2.setEmail("adam.wood@mail.com");
        user2.setMobileNumber("+77471234567");
        user2.setBirthday(new Date());
        user2.setPassword("sdfj213@!");

        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> userList = userService.getAllUsers();

        Assertions.assertEquals(2, userList.size());
    }

    @Test
    public void getAllUsersWhenDoNotExistTest() {
        List<User> emptyUserList = new ArrayList<>();
        Assertions.assertEquals(emptyUserList, userService.getAllUsers());
    }

//    @Test
//    public void getUserByExistingIdTest() {
//        long id = 3L;
//        String firstName = "Eva";
//        String lastName = "Cornell";
//        String email = "eva.cornell@test.com";
//        String password = "qwer123";
//        String mobileNumber = "+77757654321";
//        Date birthday = new Date();
////        Set<News> news = new HashSet<>();
//        Role role = new Role(USER_ROLE_ID, USER_ROLE_NAME);
//
//        User user = new User(id, email, password, firstName, lastName, mobileNumber, birthday, role);
//        when(userRepository.findById(id)).thenReturn(Optional.of(user));
//
//        User foundUser = userService.getUserById(id);
//        Assertions.assertEquals(firstName, foundUser.getFirstName());
//        Assertions.assertEquals(lastName, foundUser.getLastName());
//        Assertions.assertEquals(email, foundUser.getEmail());
//        Assertions.assertEquals(password, foundUser.getPassword());
//        Assertions.assertEquals(mobileNumber, foundUser.getMobileNumber());
//        Assertions.assertEquals(birthday, foundUser.getBirthday());
//        Assertions.assertEquals(news, foundUser.getNews());
//    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByNonExistentIdTest() {
        long nonExistentId = 123L;
        userService.getUserById(nonExistentId);
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByNullIdTest() {
        Long nullId = null;
        userService.getUserById(nullId);
    }

//    @Test
//    public void getUserByExistingEmailTest() {
//        long id = 4L;
//        String firstName = "Frank";
//        String lastName = "Underwood";
//        String email = "frank.underwood@gmail.org";
//        String password = "123QWer@";
//        String mobileNumber = "+77017654321";
//        Date birthday = new Date();
//        Set<News> news = new HashSet<>();
//        Role role = new Role(USER_ROLE_ID, USER_ROLE_NAME);
//
//        User user = new User(id, email, password, firstName, lastName, mobileNumber, birthday, news, role);
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//
//        User foundUser = userService.getUserByEmail(email);
//        Assertions.assertEquals(firstName, foundUser.getFirstName());
//        Assertions.assertEquals(lastName, foundUser.getLastName());
//        Assertions.assertEquals(email, foundUser.getEmail());
//        Assertions.assertEquals(password, foundUser.getPassword());
//        Assertions.assertEquals(mobileNumber, foundUser.getMobileNumber());
//        Assertions.assertEquals(birthday, foundUser.getBirthday());
//        Assertions.assertEquals(news, foundUser.getNews());
//    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByNonExistentEmailTest() {
        String nonExistentEmail = "non_existent@email.com";
        userService.getUserByEmail(nonExistentEmail);
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByNullEmailTest() {
        userService.getUserByEmail(null);
    }
}
