package com.BookPipeline.BookPipeline.login.controller;


import com.BookPipeline.BookPipeline.login.filter.PasswordValidator;
import com.BookPipeline.BookPipeline.login.model.ChangingPassword;
import com.BookPipeline.BookPipeline.login.model.ResponseMessage;
import com.BookPipeline.BookPipeline.login.model.UserEntity;
import com.BookPipeline.BookPipeline.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "This is for getting a user by name, for users and admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
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

    @Operation(summary = "This is for getting all users, for users and admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "404", description = "Users not found")
    })
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        if (userService.findAll() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
        }
    }

    @Operation(summary = "This is for deleting a user, for admins only")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
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

    @Operation(summary = "This is for updating a user, for admins only. Updates only the fields that are set.\n" +
    "eg. {\"role\": \"ROLE_ADMIN\"} to set user with id to admin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateUserById(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
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
            return new ResponseEntity<>(new ResponseMessage("Done!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Not found"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "This is for deleting the current user (Deleting you account), for users and admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/me")
    public ResponseEntity<ResponseMessage> deleteUser(Principal principal) {
        String username = principal.getName();
        UserEntity user = userService.findByUsername(username);
        if (user != null) {
            userService.deleteById(user.getId());
            return new ResponseEntity<>(new ResponseMessage("User deleted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("User not found"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "This is for changing the password of the current user (Change your password), for users and admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, details in message"),
    })
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/changePass")
    public ResponseEntity<ResponseMessage> changeUserPass(Principal principal, @RequestBody ChangingPassword changingPassword) {
        UserEntity user = userService.findByUsername(principal.getName());
        String oldPass = changingPassword.getOldPassword();
        String newPass = changingPassword.getNewPassword();
        String confirmPass = changingPassword.getConfirmPassword();

        if (passwordEncoder.matches(oldPass, user.getPassword())) {
            if (newPass.equals(confirmPass)) {
                String badPass = PasswordValidator.validatePassword(newPass);
                if (!badPass.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(badPass));
                }
                user.setPassword(passwordEncoder.encode(newPass));
                userService.updateUserById(user.getId(),user);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Password changed successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("New password and confirm password do not match"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Old password is incorrect"));
        }
    }
}
