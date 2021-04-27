package com.nixsolutions.ppp.controller;

import com.nixsolutions.ppp.exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class UserExceptionController {
    private static final Logger LOG = LogManager.getLogger(UserNotFoundException.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(UserNotFoundException exception) {
        LOG.error("User not found", exception);
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeException(RuntimeException exception) {
        LOG.error("Runtime Error", exception);
        return new ResponseEntity<>("Database Error", HttpStatus.CONFLICT);
    }
}
