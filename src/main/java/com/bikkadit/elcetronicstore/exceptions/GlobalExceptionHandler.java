package com.bikkadit.elcetronicstore.exceptions;

import com.bikkadit.elcetronicstore.payloads.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

        log.info("ResourceNotFoundException is initialized !!!");
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, true, HttpStatus.NOT_FOUND);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {

        log.info("handleMethodArgsNotValidException is initialized !!!");

        Map<String, String> resp = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName, message);
        });

        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadApiException.class)
    public ResponseEntity<ApiResponse> badApiExceptionHandler(BadApiException ex) {

        log.info("BadApiException is initialized !!!");

        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
