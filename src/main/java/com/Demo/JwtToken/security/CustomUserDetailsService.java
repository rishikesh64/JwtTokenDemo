package com.Demo.JwtToken.security;

import com.Demo.JwtToken.entity.User;
import com.Demo.JwtToken.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        System.out.println("================================");
        System.out.println("loadUserByUsername() Called");
        System.out.println("Searching User : " + username);

        User user = repository.findByUsername(username)

                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found"));

        System.out.println("User Found");

        return new CustomUserDetails(user);

    }
}