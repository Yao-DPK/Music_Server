/* package com.music_server.mvp.services.impl;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.services.AuthService;
import com.music_server.mvp.services.MusicUserDetailsService;
import com.music_server.mvp.services.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
public class AuthServiceImpl implements AuthService{
    
    //private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    //private final PasswordEncoder passwordEncoder;
    

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService, PasswordEncoder passwordEncoder){
        //this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        //this.passwordEncoder = passwordEncoder;
    }

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.expirationMs}")
    private Long jwtExpiryMs;

    //private final Long jwtExpiryMs = 86400000L;

    @Override
    public UserDetails authenticate(String username, String password) {
         authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
            ); 
        return userDetailsService.loadUserByUsername(username);

    }
    

    @Override
    public String generateAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        

        return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+ jwtExpiryMs))
        .signWith(getSigninKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    @Override
    public UserDetails validateToken(String token) {
        String username = extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public void register(String username, String password) {
        if(userService.existsByUsername(username)) {
            throw new RuntimeException("Username already taken");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(password);

        userService.create(newUser);
    }
    

    private Key getSigninKey(){
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String extractUsername(String token) {
        
        Claims claims = Jwts.parserBuilder()
        .setSigningKey(getSigninKey())
        .build()
        .parseClaimsJws(token)
        .getBody();

        return claims.getSubject();
    }

    
}
 */