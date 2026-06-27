package com.Demo.JwtToken.service;

import com.Demo.JwtToken.dto.AuthResponse;
import com.Demo.JwtToken.dto.LoginRequest;
import com.Demo.JwtToken.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}