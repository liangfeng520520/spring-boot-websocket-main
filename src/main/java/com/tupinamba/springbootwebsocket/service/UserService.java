package com.tupinamba.springbootwebsocket.service;

import com.tupinamba.springbootwebsocket.exception.UserNotFoundException;
import com.tupinamba.springbootwebsocket.model.User;
import com.tupinamba.springbootwebsocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public boolean authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Compare passwords (consider using a password encoder)
            return user.getPassword().equals(password);
        }
        return false;
    }

    public String registerUser(String username, String password, String email) {
        // Check if username already exists
        if (userRepository.findByUsername(username).isPresent()) {
            return "Username already taken.";
        }



        // Create new user and save to database
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // Consider encoding the password
        newUser.setEmail(email);
        newUser.setRegistrationDate(new Date(System.currentTimeMillis()));
        newUser.setActive(true);

        try {
            userRepository.save(newUser);
            return "Registration successful!";
        } catch (Exception e) {
            // 处理保存用户时可能的异常
            return "An error occurred while registering the user.";
        }

    }

    public String updateUser(User user) {
        try {
            User existingUser = userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword())); // Consider encoding the password

            userRepository.save(existingUser);
            return "Account updated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while updating the account.";
        }
    }
}