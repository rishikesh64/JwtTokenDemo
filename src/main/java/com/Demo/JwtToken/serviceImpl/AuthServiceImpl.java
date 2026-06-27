package com.Demo.JwtToken.serviceImpl;

import com.Demo.JwtToken.dto.AuthResponse;
import com.Demo.JwtToken.dto.LoginRequest;
import com.Demo.JwtToken.dto.RegisterRequest;
import com.Demo.JwtToken.entity.User;
import com.Demo.JwtToken.repository.UserRepository;

import com.Demo.JwtToken.security.JwtService;
import com.Demo.JwtToken.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {

        if (repository.findByUsername(request.getUsername()).isPresent()) {

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

    @Override
    public AuthResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(

                                request.getUsername(),

                                request.getPassword()

                        )

                );

        System.out.println("--------------------------------");

        System.out.println("Authentication Successful");

        System.out.println(authentication.getName());

        System.out.println(authentication.getAuthorities());

        System.out.println("--------------------------------");

        String token =
                jwtService.generateToken(

                        (UserDetails) authentication.getPrincipal()

                );
        System.out.println("Generated Token:");
        System.out.println(token);

        return AuthResponse.builder()
                .accessToken(token)
                .message("Login Successful")
                .build();


    }

}