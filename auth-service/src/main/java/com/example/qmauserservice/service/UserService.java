package com.example.qmauserservice.service;


import com.example.qmauserservice.dto.LoginDto;
import com.example.qmauserservice.dto.RegisterDto;
import com.example.qmauserservice.entity.User;

public interface UserService {
    User register(RegisterDto registerDto);
    User login(LoginDto loginDto);
    User getProfile(Long id);
}
