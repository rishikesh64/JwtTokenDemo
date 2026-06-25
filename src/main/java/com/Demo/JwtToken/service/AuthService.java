package com.Demo.JwtToken.service;

import com.Demo.JwtToken.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

}