package com.decentralizer.spreadr.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
class RestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        logger.error("[{}]", ex.getConstraintViolations());
        return ResponseEntity.badRequest().body(ex);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = ex.getName() + " should be of type of " + Objects.requireNonNull(ex.getRequiredType()).getName();
        logger.error("[{}] : [{}]", error, ex);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler({MethodNotAllowedException.class})
    public ResponseEntity<Object> handleMethodNotAllowedException(MethodNotAllowedException ex) {
        logger.error("[{}]", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
