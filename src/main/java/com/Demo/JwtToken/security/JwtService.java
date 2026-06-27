package com.Demo.JwtToken.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.DoubleStream;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private Long jwtExpiration;

    private Key getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        System.out.println("=================================");
        System.out.println("Generating JWT");
        System.out.println("Username : " + userDetails.getUsername());
        System.out.println("Issued At : " + new Date());
        System.out.println("Expiry After : " + jwtExpiration + " ms");
        System.out.println("=================================");

        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + jwtExpiration)
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Claims extractAllClaims(String token) {

        System.out.println("==========================");

        System.out.println("Parsing JWT");

        System.out.println("Token");

        System.out.println(token);

        Claims claims =

                Jwts

                        .parserBuilder()

                        .setSigningKey(getSigningKey())

                        .build()

                        .parseClaimsJws(token)

                        .getBody();

        System.out.println("Username : " + claims.getSubject());

        System.out.println("==========================");

        return claims;

    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        System.out.println("========================");
        System.out.println("extractClaim() Called");

        Claims claims = extractAllClaims(token);

        T value = claimsResolver.apply(claims);

        System.out.println("Extracted Value : " + value);

        System.out.println("========================");

        return value;
    }

    public String extractUsername(String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );

    }

    public Date extractExpiration(String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        );

    }

    public boolean isTokenExpired(String token) {

        Date expiration = extractExpiration(token);

        Date current = new Date();

        System.out.println("========================");

        System.out.println("Checking Token Expiry");

        System.out.println("Current Time : " + current);

        System.out.println("Expiry Time  : " + expiration);

        System.out.println("Expired ? : " + expiration.before(current));

        System.out.println("========================");

        return expiration.before(current);

    }

    public boolean validateToken(
            String token,
            UserDetails userDetails
    ) {

        System.out.println("========================");

        System.out.println("Validating Token");

        String username = extractUsername(token);

        System.out.println("Token Username : " + username);

        System.out.println("Logged User    : " + userDetails.getUsername());

        boolean valid =

                username.equals(userDetails.getUsername())

                        &&

                        !isTokenExpired(token);

        System.out.println("Token Valid : " + valid);

        System.out.println("========================");

        return valid;

    }

}
