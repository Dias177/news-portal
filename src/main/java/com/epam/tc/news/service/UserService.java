package com.epam.tc.news.service;

import com.epam.tc.news.entity.Role;
import com.epam.tc.news.entity.User;
import com.epam.tc.news.exception.UserNotFoundException;
import com.epam.tc.news.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserService implements UserDetailsService {

    static Long ROLE_USER_ID = 1L;
    static String ROLE_USER_NAME = "ROLE_USER";

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
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
        Long id = user.getId();
        String email = user.getEmail();
        User newUser = null;
        if (!userExists(id, email)) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(new Role(ROLE_USER_ID, ROLE_USER_NAME)));
            newUser = userRepository.save(user);
        }
        return newUser;
    }

    public User createOrUpdateUser(User user, Long id) {
        return userRepository.findById(id).map(u -> {
            u.setEmail(user.getEmail());
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setBirthday(user.getBirthday());
            u.setRoles(Collections.singleton(new Role(ROLE_USER_ID, ROLE_USER_NAME)));
            u.setMobileNumber(user.getMobileNumber());
            u.setPassword(user.getPassword());
            u.setNews(user.getNews());
            return userRepository.save(u);
        }).orElseGet(() -> {
            user.setId(id);
            user.setRoles(Collections.singleton(new Role(ROLE_USER_ID, ROLE_USER_NAME)));
            return userRepository.save(user);
        });
    }

    public void deleteUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found id: " + id);
        }
    }

    public boolean userExists(Long id, String email) {
        Optional<User> userWithId = userRepository.findById(id);
        Optional<User> userWithEmail = userRepository.findByEmail(email);
        return userWithId.isPresent() || userWithEmail.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found username: " + username));
    }
}
