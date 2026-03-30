package org.quantitymeasurement.app.controller;

import org.quantitymeasurement.app.dto.ApiResponseDto;
import org.quantitymeasurement.app.dto.LoginDto;
import org.quantitymeasurement.app.dto.RegisterDto;
import org.quantitymeasurement.app.entity.User;
import org.quantitymeasurement.app.security.jwt.JwtService;
import org.quantitymeasurement.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Value("${spring.application.token.expiry}")
    private long tokenExpiry;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponseDto<?>> register(@RequestBody RegisterDto registerDto){
        userService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(true, "Registered successfully"));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponseDto<User>> loginUser(@RequestBody LoginDto loginDto) {
        User user = userService.login(loginDto);
        String Token = jwtService.generateToken(user);
        return ResponseEntity.ok().header( "Set-Cookie", String.format( "jwt=%s; Path=/; Max-Age=%d; HttpOnly; SameSite=Lax", Token, tokenExpiry ) ) .body(new ApiResponseDto<>(true,"Logged In", user));
    }

    @GetMapping("/auth/session")
    public ResponseEntity<ApiResponseDto<User>> session(Authentication authentication){
        if(authentication == null || !authentication.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto<>(false, "session not found", null));
        }
        Long principal = (Long) authentication.getPrincipal();
        User user = userService.getProfile(principal);
        return ResponseEntity.ok().body(new ApiResponseDto<>(true, "Session found", user));
    }

    @GetMapping("/auth/sessions/logout")
    public ResponseEntity<?> logout() {

        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiResponseDto<>(true, "Logged out", null));
    }
}
