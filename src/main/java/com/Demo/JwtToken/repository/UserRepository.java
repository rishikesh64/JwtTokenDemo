package com.Demo.JwtToken.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Demo.JwtToken.entity.User;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}