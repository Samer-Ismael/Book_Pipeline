package com.BookPipeline.BookPipeline.login.config;

import com.BookPipeline.BookPipeline.login.filter.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// SecurityConfig is a configuration class for Spring Security.
// It is annotated with @Configuration to indicate that it's a source of bean definitions.
// It is also annotated with
// @EnableWebSecurity and
// @EnableMethodSecurity to enable Spring Security's web security support and enable method level security respectively.
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private JWTAuthFilter jwtAuthFilter;
    private static final String[] AUTH_WHITELIST = {
            "/auth/register",
            "/auth/login",
            "/articles",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui",
            "/swagger-ui/",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    // Constructor for SecurityConfig, it takes a UserDetailsService and a JWTAuthFilter as parameters.
    public SecurityConfig(UserDetailsService userDetailsService, JWTAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    // filterChain method configures the HttpSecurity to use JWT for security.
    // It disables CSRF, sets session management to stateless, and adds the JWTAuthFilter before the UsernamePasswordAuthenticationFilter.
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.authorizeHttpRequests(auth ->{
                auth.requestMatchers(AUTH_WHITELIST).permitAll();
                auth.anyRequest().authenticated();
                });

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.userDetailsService(userDetailsService);
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

    // passwordEncoder method provides a BCryptPasswordEncoder bean.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // authenticationProvider method provides a DaoAuthenticationProvider bean.
    // It sets the UserDetailsService and PasswordEncoder for the DaoAuthenticationProvider.
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}