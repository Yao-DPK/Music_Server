/* package com.music_server.mvp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.music_server.mvp.services.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final AuthService authService;

    @Autowired
    public JwtAuthenticationFilter(AuthService authService){
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String token = extractAccessTokenFromCookies(request);
        if(token != null){
            UserDetails userDetails = authService.validateToken(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if(userDetails instanceof MusicUserDetails){
                request.setAttribute("userId", ((MusicUserDetails) userDetails).getId());
            }
        }
        } catch (Exception e) {
            // Just don't authenticate the User

            System.out.println("Received invalid auth token: " + e);
        }
        
        filterChain.doFilter(request, response);
    }

    private String extractAccessTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("access_token")) {
                return c.getValue();
            }
        }
        return null;
    }


}
 */