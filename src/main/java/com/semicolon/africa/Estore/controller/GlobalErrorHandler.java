package com.semicolon.africa.Estore.controller;

import com.semicolon.africa.Estore.dtos.response.ApiResponse;
import com.semicolon.africa.Estore.exceptions.EstoreExceptions;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(EstoreExceptions.class)
    public ResponseEntity<?> handleEstoreException(EstoreExceptions e) {
        return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<?> handlePSQLException(PSQLException e) {
        String errorMessage = "";
        if(e.getMessage().contains("(email)")) errorMessage = "Email Already Exists";

        else if(e.getMessage().contains("uk_3gnjncn8l4no25wl7pyjqrx3p")) errorMessage = "Username Already Exists";
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false,errorMessage));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String defaultMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return new ResponseEntity<>(new ApiResponse(false,defaultMessage), HttpStatus.BAD_REQUEST);
    }
}
