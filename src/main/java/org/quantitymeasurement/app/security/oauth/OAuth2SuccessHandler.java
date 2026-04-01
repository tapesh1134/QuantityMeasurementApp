package org.quantitymeasurement.app.security.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.quantitymeasurement.app.entity.User;
import org.quantitymeasurement.app.repository.UserRepository;
import org.quantitymeasurement.app.security.jwt.JwtService;
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

        // 🔹 Extract data from OAuth (Google)
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        if (email == null) {
            throw new RuntimeException("Email not found from OAuth provider");
        }

        // 🔹 Find existing user
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            // 🔹 Split name safely
            String firstName = "";
            String lastName = "";

            if (name != null) {
                String[] parts = name.split(" ");
                firstName = parts[0];
                if (parts.length > 1) {
                    lastName = parts[1];
                }
            }

            // 🔹 Create new OAuth user
            user = User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .provider("GOOGLE")
                    .password(null)
                    .build();

            userRepository.save(user);
        }
        //  Generate JWT
        String token = jwtService.generateToken(user);

        response.addHeader(
                "Set-Cookie",
                String.format(
                        "jwt=%s; Path=/; Max-Age=%d; HttpOnly; SameSite=Lax",
                        token,
                        tokenExpiry
                )
        );

        //  Redirect
        getRedirectStrategy().sendRedirect(
                request,
                response,
                "http://localhost:8080"
        );
    }
}