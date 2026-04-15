//package com.example.qmauserservice.controller;
//
//import com.example.qmauserservice.dto.ApiResponseDto;
//import com.example.qmauserservice.entity.User;
//import com.example.qmauserservice.repository.UserRepository;
//import com.example.qmauserservice.security.jwt.JwtService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class VerificationController {
//    private final UserRepository userRepository;
//    private final JwtService  jwtService;
//
//    @Autowired
//    public VerificationController(UserRepository userRepository, JwtService jwtService) {
//        this.userRepository = userRepository;
//        this.jwtService = jwtService;
//    }
//
//    @GetMapping("/req/signup/verify")
//    public ResponseEntity<ApiResponseDto<?>> verifyEmail(@RequestParam("token") String token){
//        Long userId = jwtService.extractUserId(token);
//        User user = userRepository.findById(userId).orElse(null);
//
//        if(user == null || user.getVerificationToken() == null){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto<>(false, "Invalid token"));
//        }
//        if(!jwtService.validateToken(token) || !user.getVerificationToken().equals(token)){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto<>(false, "Invalid token"));
//        }
//        user.setVerificationToken(token);
//        user.setVerified(true);
//        userRepository.save(user);
//
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>(true, "User has been verified"));
//    }
//}
