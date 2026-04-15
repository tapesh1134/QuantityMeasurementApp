package com.example.qmauserservice.controller;

import com.example.qmauserservice.dto.ApiResponseDto;
import com.example.qmauserservice.dto.LoginDto;
import com.example.qmauserservice.dto.RegisterDto;
import com.example.qmauserservice.entity.User;
import com.example.qmauserservice.repository.UserRepository;
import com.example.qmauserservice.security.jwt.JwtService;
import com.example.qmauserservice.service.EmailService;
import com.example.qmauserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
//    private final EmailService emailService;
    @Value("${spring.application.token.expiry}")
    private long tokenExpiry;
    private final UserService userService;
    private final JwtService jwtService;
//    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
//        this.userRepository = userRepository;
//        this.emailService = emailService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponseDto<?>> register(@RequestBody RegisterDto registerDto){
//        User  existingUser = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
//
//        if(existingUser != null){
//            if(existingUser.isVerified()){
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>(false, "Email already exists"));
//            } else {
//                String verificationToken = jwtService.generateToken(existingUser);
//                existingUser.setVerificationToken(verificationToken);
//                userRepository.save(existingUser);
//                emailService.sendVerificationEmail(existingUser.getEmail(), verificationToken);
//                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>(true, "Verification Email resent. Check your inbox"));
//            }
//        }
        userService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(true, "Registration successfull! Please Verify your Email"));
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
