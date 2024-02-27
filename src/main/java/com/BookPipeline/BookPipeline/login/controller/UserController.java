package com.BookPipeline.BookPipeline.login.controller;


import com.BookPipeline.BookPipeline.login.filter.PasswordValidator;
import com.BookPipeline.BookPipeline.login.model.ChangingPassword;
import com.BookPipeline.BookPipeline.login.model.UserEntity;
import com.BookPipeline.BookPipeline.login.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{name}")
    public ResponseEntity<UserEntity> getUserByName(@PathVariable String name) {
        UserEntity user = userService.findByUsername(name);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }


    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        if (userService.findAll() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable Long id) {
        if (userService.existsById(id)) {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        if (updatedUser.getPassword() != null) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if (userService.existsById(id)) {
            UserEntity newUser = userService.findById(id).get();
            // that will make it easier to update the user, if the field is null, it will not be updated
            if (updatedUser.getPassword() == null) updatedUser.setPassword(newUser.getPassword());
            if (updatedUser.getRole() == null) updatedUser.setRole(newUser.getRole());
            if (updatedUser.getUsername() == null) updatedUser.setUsername(newUser.getUsername());

            userService.updateUserById(id, updatedUser);
            return new ResponseEntity<>("Done!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteUser(Principal principal) {
        String username = principal.getName();
        UserEntity user = userService.findByUsername(username);
        if (user != null) {
            userService.deleteById(user.getId());
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/changePass")
    public ResponseEntity<String> changeUserPass(Principal principal, @RequestBody ChangingPassword changingPassword) {
        UserEntity user = userService.findByUsername(principal.getName());
        String oldPass = changingPassword.getOldPassword();
        String newPass = changingPassword.getNewPassword();
        String confirmPass = changingPassword.getConfirmPassword();

        if (passwordEncoder.matches(oldPass, user.getPassword())) {
            if (newPass.equals(confirmPass)) {
                String badPass = PasswordValidator.validatePassword(newPass);
                if (!badPass.equals("")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badPass);
                }
                user.setPassword(passwordEncoder.encode(newPass));
                userService.save(user);
                return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password and confirm password do not match");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
        }
    }
}
