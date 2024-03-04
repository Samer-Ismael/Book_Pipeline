package com.BookPipeline.BookPipeline.login.controller;


import com.BookPipeline.BookPipeline.login.filter.PasswordValidator;
import com.BookPipeline.BookPipeline.login.model.*;
import com.BookPipeline.BookPipeline.login.service.JWTService;
import com.BookPipeline.BookPipeline.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// UserController is a REST controller that handles user-related requests.
// It is annotated with @RestController to indicate that it's a controller where every method returns a domain object instead of a view.
// It's also annotated with @RequestMapping("/users") to map web requests onto specific handler classes and/or handler methods.
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    // Constructor for UserController, it takes an AuthenticationManager, UserService, PasswordEncoder, and JWTService as parameters.
    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    //----------------------------------------------------------------------
    // Login and register Methods here...
    //----------------------------------------------------------------------
    // The register method handles the registration of new users.
    // It checks if the username already exists, if not, it creates a new user with the provided details.
    @Operation(summary = "This is for registering a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "User not registered successfully, details in message")
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@RequestBody AuthRequest user) {

        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Username already exists"));
        } else {

            String badPass = PasswordValidator.validatePassword(user.getPassword());
            if (!badPass.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(badPass));
            }
            UserEntity newUser = new UserEntity();
            newUser.setUsername(user.getUsername());
            newUser.setRole(Roles.ROLE_USER);
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));

            userService.save(newUser);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Registration successful!"));
        }
    }

    // The authAndGetToken method handles the authentication of users.
    // It authenticates the user and if successful, generates a JWT token for the user.
    // Here you can ask for payment or something else before generating the token.
    @Operation(summary = "This is for logging in a user and getting a token for the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "User not logged in successfully", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseToken> authAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok(new ResponseToken(jwtService.generateToken(authRequest.getUsername())));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
