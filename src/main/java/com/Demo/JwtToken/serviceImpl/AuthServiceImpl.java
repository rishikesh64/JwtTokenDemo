package com.Demo.JwtToken.serviceImpl;

import com.Demo.JwtToken.dto.RegisterRequest;
import com.Demo.JwtToken.entity.User;
import com.Demo.JwtToken.repository.UserRepository;

import com.Demo.JwtToken.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    @Override
    public String register(RegisterRequest request) {

        if(repository.findByUsername(request.getUsername()).isPresent()) {

            return "Username Already Exists";

        }

        User user = User.builder()

                .username(request.getUsername())

                .email(request.getEmail())

                .password(encoder.encode(request.getPassword()))

                .role("USER")

                .build();

        repository.save(user);

        System.out.println("--------------------------------");

        System.out.println("User Saved Successfully");

        System.out.println("Username : " + user.getUsername());

        System.out.println("Password Hash : " + user.getPassword());

        System.out.println("--------------------------------");

        return "Registration Successful";

    }

}