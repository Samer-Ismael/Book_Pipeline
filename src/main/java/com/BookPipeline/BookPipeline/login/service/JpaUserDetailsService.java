package com.BookPipeline.BookPipeline.login.service;

import com.BookPipeline.BookPipeline.login.model.SecurityUser;
import com.BookPipeline.BookPipeline.login.model.UserEntity;
import com.BookPipeline.BookPipeline.login.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// JpaUserDetailsService is a service class that implements UserDetailsService for user authentication.
// It is annotated with @Service to indicate that it's a bean to be managed by Spring.
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    // Constructor for JpaUserDetailsService, it takes a UserRepo as a parameter.
    public JpaUserDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    // loadUserByUsername method loads a user by their username.
    // It returns a UserDetails object if a user with the given username exists, throws a UsernameNotFoundException otherwise.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            // If a user with the given username exists, a new SecurityUser is created from the UserEntity and returned.
            return new SecurityUser(optionalUser.get());
        } else {
            // If a user with the given username does not exist, a UsernameNotFoundException is thrown.
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}