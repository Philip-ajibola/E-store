package com.semicolon.africa.Estore.controller;

import com.github.fge.jsonpatch.JsonPatchException;
import com.google.gson.JsonParseException;
import com.semicolon.africa.Estore.dtos.response.ApiResponse;
import com.semicolon.africa.Estore.exceptions.EstoreExceptions;
import jakarta.mail.MessagingException;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(EstoreExceptions.class)
    @ResponseBody
    public ResponseEntity<?> handleEstoreException(EstoreExceptions e) {
        return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PSQLException.class)
    @ResponseBody
    public ResponseEntity<?> handlePSQLException(PSQLException e) {
        String errorMessage = "";
        if(e.getMessage().contains("(email)")) errorMessage = "Email Already Exists";

        else if(e.getMessage().contains("uk_3gnjncn8l4no25wl7pyjqrx3p")) errorMessage = "Username Already Exists";
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false,errorMessage));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String defaultMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return new ResponseEntity<>(new ApiResponse(false,defaultMessage), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResponseEntity<?> handleIOException(IOException e) {
        return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(JsonPatchException.class)
    @ResponseBody
    public ResponseEntity<?> handleJsonPatchException(JsonPatchException e) {
        return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MessagingException.class)
    @ResponseBody
    public ResponseEntity<?> handleMessagingException(MessagingException e) {
        return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
