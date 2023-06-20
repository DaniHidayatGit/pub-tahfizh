package com.tugasakhir.util;

import com.tugasakhir.configuration.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdviceExeption {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNotFoundExeption(Exception e) {
        Object[] objects = {};
        log.error("{}", e.getMessage());
        return Response.response(objects, e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(Exception e) {
        Object[] objects = {};
        log.error("{}", e.getMessage());
        return Response.response(objects, e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
