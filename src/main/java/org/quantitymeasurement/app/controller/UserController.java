package org.quantitymeasurement.app.controller;

import org.quantitymeasurement.app.dto.LoginDto;
import org.quantitymeasurement.app.dto.RegisterDto;
import org.quantitymeasurement.app.entity.User;
import org.quantitymeasurement.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDto registerDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(loginDto));
    }
}
