package com.intuit.sportseventsregistration.exceptions;

import com.intuit.sportseventsregistration.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SportsEventRegistrationException.class)
    public ResponseEntity<ErrorResponse> handleSportsEventRegistrationException(SportsEventRegistrationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
