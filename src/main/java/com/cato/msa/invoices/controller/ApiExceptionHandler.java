package com.cato.msa.invoices.controller;

import com.cato.msa.invoices.exceptions.NotContentException;
import com.cato.msa.invoices.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NotContentException.class)
    ResponseEntity<Void> handleNotContentException(NotContentException ex){
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<?> handleNotFoundException(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Error retrieving invoice", ex.getMessage()));
    }
    static class ErrorResponse {
        public String code;
        public String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
