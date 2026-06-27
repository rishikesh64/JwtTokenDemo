package com.Demo.JwtToken.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain

    )
            throws ServletException, IOException {

        System.out.println("========================");
        System.out.println("JWT Filter Executed");

        final String authHeader =
                request.getHeader("Authorization");

        System.out.println("Header : " + authHeader);

        if(authHeader == null ||
                !authHeader.startsWith("Bearer ")){

            filterChain.doFilter(request,response);

            return;

        }

        String jwt =
                authHeader.substring(7);

        System.out.println("JWT");

        System.out.println(jwt);

        String username =
                jwtService.extractUsername(jwt);

        System.out.println("Username : " + username);

        System.out.println("========================");

        filterChain.doFilter(request,response);

    }
}