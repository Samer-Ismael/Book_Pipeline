package com.BookPipeline.BookPipeline.login.filter;


import com.BookPipeline.BookPipeline.login.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JWTAuthFilter is a custom filter for JWT authentication.
// It extends OncePerRequestFilter to ensure a single execution per request dispatch.
@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    // Constructor for JWTAuthFilter, it takes a JWTService and a UserDetailsService as parameters.
    public JWTAuthFilter(JWTService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    // doFilterInternal method is overridden from OncePerRequestFilter.
    // It extracts the JWT token from the request header, validates it, and sets the authentication in the context.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Extract the Authorization header from the request.
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // If the Authorization header is present and starts with "Bearer ", extract the token.
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.getUserNameFromToken(token);
        }

        // If the username is not null and there is no authentication in the context, validate the token and set the authentication.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails user = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continue the filter chain.
        filterChain.doFilter(request, response);
    }
}