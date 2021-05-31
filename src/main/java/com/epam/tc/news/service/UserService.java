package com.epam.tc.news.service;

import com.epam.tc.news.entity.User;
import com.epam.tc.news.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User not found id: " + id);
        }
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User not found email: " + email);
        }
    }

    public User createUser(User user) {
        Optional<User> u = userRepository.findById(user.getId());
        User newUser;
        if (u.isPresent()) {
            newUser = u.get();
            newUser.setEmail(user.getEmail());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setBirthday(user.getBirthday());
            newUser.setPassword(user.getPassword());
            newUser.setMobileNumber(user.getMobileNumber());
            newUser = userRepository.save(newUser);
        } else {
            newUser = userRepository.save(user);
        }
        return newUser;
    }

    public void deleteUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found id: " + id);
        }
    }
}
