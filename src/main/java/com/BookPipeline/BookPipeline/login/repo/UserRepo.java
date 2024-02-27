package com.BookPipeline.BookPipeline.login.repo;

import com.BookPipeline.BookPipeline.login.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    // Method to check if a user exists by their username.
    // Returns true if a user with the given username exists, false otherwise.
    // This method is useful for checking the existence of a user before creating a new one,
    // to avoid duplicate usernames.
    boolean existsByUsername(String username);

    // Method to check if a user exists by their id.
    // Returns true if a user with the given id exists, false otherwise.
    // This method is useful for checking the existence of a user before performing operations like update or delete.
    boolean existsById(Long id);



    // Method to find a user by their username.
    // Returns an Optional containing the UserEntity if a user with the given username exists,
    // an empty Optional otherwise.
    // Optional is used here to allow the repository to return null in a more elegant way when the user is not found.
    // This method is useful for operations like login, where you need to fetch the user details by username.
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findById (long id);

}