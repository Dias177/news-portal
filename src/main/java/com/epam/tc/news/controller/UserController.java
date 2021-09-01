package com.epam.tc.news.controller;

import com.epam.tc.news.dto.UserRequestDto;
import com.epam.tc.news.dto.UserResponseDto;
import com.epam.tc.news.entity.User;
import com.epam.tc.news.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserController {

    UserService userService;
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        users.forEach(u -> userResponseDtos.add(convertToDto(u)));
        return ResponseEntity.ok(userResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") long id) {
        var user = userService.getUserById(id);
        return ResponseEntity.ok(convertToDto(user));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto user) {
        var u = convertToEntity(user);
        u = userService.createUser(u);
        return new ResponseEntity<>(convertToDto(u), u == null ? HttpStatus.CONFLICT : HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> createOrUpdateUser(@RequestBody UserRequestDto user, @PathVariable Long id) {
        var u = convertToEntity(user);
        u = userService.createOrUpdateUser(u, id);
        return new ResponseEntity<>(convertToDto(u), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") long id) {
        userService.deleteUserById(id);
    }

    private User convertToEntity(UserRequestDto userRequestDto) {
        return modelMapper.map(userRequestDto, User.class);
    }

    private UserResponseDto convertToDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }
}
