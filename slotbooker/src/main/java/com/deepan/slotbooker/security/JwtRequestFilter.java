package com.deepan.slotbooker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A filter that intercepts incoming requests to check for a valid JWT in the Authorization header.
 * It authenticates the user if a valid token is present.
 */
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    /**
     * This is the core method of the filter. It's called for every incoming request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // Get the Authorization header from the request.
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the JWT from the header.
            jwt = authorizationHeader.substring(7);
            // Use the JwtUtil to get the username from the token.
            username = jwtUtil.extractUsername(jwt);
        }

        // If we found a username and the current security context is not already authenticated...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details from our custom service.
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            // Validate the token against the loaded user details.
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // If the token is valid, create a new authentication token.
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the user's authentication in the SecurityContext.
                // This tells Spring Security that the user is authenticated for this request.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // Pass the request along the filter chain.
        chain.doFilter(request, response);
    }
}
