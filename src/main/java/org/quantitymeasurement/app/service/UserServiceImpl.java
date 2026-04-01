package org.quantitymeasurement.app.service;


import org.quantitymeasurement.app.dto.LoginDto;
import org.quantitymeasurement.app.dto.RegisterDto;
import org.quantitymeasurement.app.entity.User;
import org.quantitymeasurement.app.repository.UserRepository;
import org.quantitymeasurement.app.security.CustomUserDetails;
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

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager,UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User register(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email is already used");
        }

        User user = User.builder().firstName(registerDto.getFirstName()).lastName(registerDto.getLastName()).provider("LOCAL").email(registerDto.getEmail()).password(bCryptPasswordEncoder.encode(registerDto.getPassword())).build();
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
