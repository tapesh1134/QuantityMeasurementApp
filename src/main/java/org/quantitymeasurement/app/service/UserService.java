package org.quantitymeasurement.app.service;

import org.quantitymeasurement.app.dto.LoginDto;
import org.quantitymeasurement.app.dto.RegisterDto;
import org.quantitymeasurement.app.entity.User;

public interface UserService {
    User register(RegisterDto registerDto);
    User login(LoginDto loginDto);
    User getProfile(Long id);
}
