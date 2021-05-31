package com.epam.tc.news.controller;

import com.epam.tc.news.dto.UserDto;
import com.epam.tc.news.entity.User;
import com.epam.tc.news.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserController {

    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto user) {
        User u = mapUserDtoToEntity(user);
        u = userService.createUser(u);
        return new ResponseEntity<>(u, u == null ? HttpStatus.CONFLICT : HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> createOrUpdateUser(@RequestBody UserDto user, @PathVariable Long id) {
        User u = mapUserDtoToEntity(user);
        u = userService.createOrUpdateUser(u, id);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") long id) {
        userService.deleteUserById(id);
    }

    private User mapUserDtoToEntity(UserDto user) {
        var u = new User();
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setBirthday(user.getBirthday());
        u.setMobileNumber(user.getMobileNumber());
        return u;
    }
}
