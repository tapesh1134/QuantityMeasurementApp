package com.example.qmauserservice.security.oauth;

import com.example.qmauserservice.entity.User;
import com.example.qmauserservice.repository.UserRepository;
import com.example.qmauserservice.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Value("${spring.application.token.expiry}")
    private long tokenExpiry;

    public OAuth2SuccessHandler(JwtService jwtService,
                                UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println(oAuth2User.toString());
        System.out.println(oAuth2User.getAttributes());
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        if (email == null) {
            throw new RuntimeException("Email not found from OAuth provider");
        }

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            String firstName = "";
            String lastName = "";

            if (name != null) {
                String[] parts = name.split(" ");
                firstName = parts[0];
                if (parts.length > 1) {
                    lastName = parts[1];
                }
            }

            user = User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .provider("GOOGLE")
                    .password(null)
                    .build();

            userRepository.save(user);
        }
        String token = jwtService.generateToken(user);

        response.addHeader(
                "Set-Cookie",
                String.format(
                        "jwt=%s; Path=/; Max-Age=%d; HttpOnly; SameSite=Lax",
                        token,
                        tokenExpiry
                )
        );

        getRedirectStrategy().sendRedirect(
                request,
                response,
                "http://localhost:5173"
        );
    }
}