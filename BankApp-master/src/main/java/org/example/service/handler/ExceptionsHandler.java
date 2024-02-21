package org.example.service.handler;

import org.example.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {AccountNotFoundException.class, ClientNotFoundException.class, ManagerNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AccountExistsException.class, ClientExistsException.class, ManagerExistsException.class})
    public ResponseEntity<String> handleEntityExistsException(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccountNotActiveException.class)
    public ResponseEntity<String> handleAccountNotActiveException(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UnsupportedTransactionException.class)
    public ResponseEntity<String> handleUnsupportedTransactionException(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    public ResponseEntity<String> handleUnsupportedOperationException(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}