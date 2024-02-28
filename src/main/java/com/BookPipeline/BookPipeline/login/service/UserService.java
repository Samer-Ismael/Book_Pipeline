package com.BookPipeline.BookPipeline.login.service;

import com.BookPipeline.BookPipeline.login.model.UserEntity;
import com.BookPipeline.BookPipeline.login.repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// UserService is a service class that handles user-related operations.
// It is annotated with @Service to indicate that it's a bean to be managed by Spring.
@Service
public class UserService {

    private final UserRepo userRepo;

    // Constructor for UserService, it takes a UserRepo as a parameter.
    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // save method saves a new user to the database.
    // It checks if a user with the given username already exists, if not, it saves the new user and returns a ResponseEntity with an OK status.
    // If a user with the given username already exists, it returns a ResponseEntity with a CONFLICT status.
    public ResponseEntity<String> save(UserEntity user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        if (user.getUsername() == null || user.getPassword() == null || user.getRole() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password cannot be empty");
        }
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password cannot be empty");
        }
        userRepo.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // findAll method retrieves all users from the database.
    // It returns a list of all UserEntity objects, or null if no users are found.
    public List<UserEntity> findAll() {
        if (userRepo.findAll().isEmpty()) {
            return Collections.emptyList();
        } else {
            userRepo.findAll();
            return new ArrayList<>(userRepo.findAll());
        }
    }

    // findByUsername method retrieves a user by their username.
    // It returns a UserEntity object if a user with the given username exists, or null otherwise.
    public UserEntity findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    // updateUserById method updates a user by their id.
    // It returns an Optional containing the updated UserEntity if a user with the given id exists, or an empty Optional otherwise.
    public Optional<UserEntity> updateUserById(Long userId, UserEntity updatedUser) {
        Optional<UserEntity> existingUser = userRepo.findById(userId);

        if (existingUser.isPresent()) {
            UserEntity userToUpdate = existingUser.get();
            userToUpdate.setUsername(updatedUser.getUsername());
            userToUpdate.setPassword(updatedUser.getPassword());
            userToUpdate.setRole(updatedUser.getRole());

            return Optional.of(userRepo.save(userToUpdate));
        } else {
            return Optional.empty();
        }
    }

    // deleteById method deletes a user by their id.
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    // existsByUsername method checks if a user exists by their username.
    // It returns true if a user with the given username exists, false otherwise.
    public Boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    // existsById method checks if a user exists by their id.
    // It returns true if a user with the given id exists, false otherwise.
    public Boolean existsById(Long id) {
        return userRepo.existsById(id);
    }

    // saveJson method saves a user to the database from a JSON string.
    // It converts the JSON string to a UserEntity object and saves it to the database.
    public void saveJson(String userJson) throws JsonProcessingException {
        // Convert the JSON string back to a UserEntity object
        ObjectMapper objectMapper = new ObjectMapper();
        UserEntity user = objectMapper.readValue(userJson, UserEntity.class);

        // Save the UserEntity object to the database
        userRepo.save(user);
    }

    public Optional<UserEntity> findById(long id) {
        return userRepo.findById(id);
    }

}