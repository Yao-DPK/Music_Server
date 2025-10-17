package com.music_server.mvp.domain.dto;

import java.util.List;

public class ApiErrorResponse {

    private int status;
    private String message;
    private List<FieldError> errors;

    // Constructors
    public ApiErrorResponse() {}

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiErrorResponse(int status, String message, List<FieldError> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    // Getters and setters
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<FieldError> getErrors() { return errors; }
    public void setErrors(List<FieldError> errors) { this.errors = errors; }

    // Nested class
    public static class FieldError {
        private String field;
        private String message;

        public FieldError() {}
        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() { return field; }
        public void setField(String field) { this.field = field; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
