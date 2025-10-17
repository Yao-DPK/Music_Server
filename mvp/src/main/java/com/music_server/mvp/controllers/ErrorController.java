package com.music_server.mvp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.music_server.mvp.domain.dto.ApiErrorResponse;

import lombok.extern.slf4j.Slf4j;


@RestController
@ControllerAdvice
public class ErrorController {

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message) {
        ApiErrorResponse error = new ApiErrorResponse(status.value(), message);
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingBody(HttpMessageNotReadableException ex) {
        System.out.println("Request body is missing or malformed : "+  ex);
        return buildResponse(HttpStatus.BAD_REQUEST, "Request body is missing or malformed");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        System.out.println("Authentication failed: "+ ex);
        return buildResponse(HttpStatus.UNAUTHORIZED, "Incorrect username or password");
    }

    @ExceptionHandler({IllegalArgumentException.class, RuntimeException.class})
    public ResponseEntity<ApiErrorResponse> handleBadRequest(RuntimeException ex) {
        System.out.println("Bad request :"+ ex);
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleOtherExceptions(Exception ex) {
        System.out.println("Unexpected exception: "+ ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<ApiErrorResponse.FieldError> fieldErrors = ex.getBindingResult().getFieldErrors()
        .stream()
        .map(err -> new ApiErrorResponse.FieldError(err.getField(), err.getDefaultMessage()))
        .toList();

    ApiErrorResponse error = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
            "Validation failed", fieldErrors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(error);
}
}