package com.example.qmauserservice.service;

import com.example.qmauserservice.dto.LoginDto;
import com.example.qmauserservice.dto.RegisterDto;
import com.example.qmauserservice.entity.User;
import com.example.qmauserservice.repository.UserRepository;
import com.example.qmauserservice.security.CustomUserDetails;
import com.example.qmauserservice.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtService jwtService, EmailService emailService){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    @Override
    public User register(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email is already used");
        }

        User user = User.builder().firstName(registerDto.getFirstName()).lastName(registerDto.getLastName()).provider("LOCAL").email(registerDto.getEmail()).password(bCryptPasswordEncoder.encode(registerDto.getPassword())).build();
//        String verificationToken = jwtService.generateToken(user);
//        emailService.sendVerificationEmail(registerDto.getEmail(), verificationToken);
        return userRepository.save(user);
    }

    @Override
    public User login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        if(authentication.isAuthenticated()){
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            return user;
        }else{
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public User getProfile(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong user id");
        }
        return user.get();
    }
}
