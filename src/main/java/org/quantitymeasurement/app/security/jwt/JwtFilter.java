package org.quantitymeasurement.app.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1️⃣ Skip public routes
        String path = request.getRequestURI();
//        if (path.startsWith("/api/auth")|| path.startsWith("/api/oauth2") || path.startsWith("/api/public")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        // 2️⃣ Extract JWT from cookie
        String token = extractJwtFromCookies(request);

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 3️⃣ Validate token
            if (jwtService.validateToken(token)) {

                // 4️⃣ Extract immutable identity
                Long userId = jwtService.extractUserId(token);

                // 5️⃣ Create Authentication with USER ID as principal
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId,                    // principal (Long)
                                null,                      // credentials
                                Collections.emptyList()    // NO roles here
                        );

                // 6️⃣ Store authentication
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 7️⃣ Continue filter chain
        filterChain.doFilter(request, response);
    }

    private String extractJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("jwt".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}