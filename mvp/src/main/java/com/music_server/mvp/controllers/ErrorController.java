package com.music_server.mvp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.music_server.mvp.domain.dto.ApiErrorResponse;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@RestController
@ControllerAdvice
public class ErrorController {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex){

        System.out.println("Caught exception" +  ex);
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occured");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentExcpetion(IllegalArgumentException ex){
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeExcpetion(RuntimeException ex){
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password");
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


}
