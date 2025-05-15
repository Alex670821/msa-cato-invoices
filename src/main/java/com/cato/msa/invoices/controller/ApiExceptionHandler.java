package com.cato.msa.invoices.controller;

import com.cato.msa.invoices.exception.NotContentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NotContentException.class)
    ResponseEntity<Void> handleNotContentException(NotContentException ex){
        return ResponseEntity.noContent().build();
    }
}
