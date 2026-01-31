package org.example.reto4ad.controllers;

import org.example.reto4ad.dto.ErrorResponseDTO;
import org.example.reto4ad.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HotelControllerAdvice {

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleHotelNotFound(HotelNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO("Resource Not Found", e.getMessage(), 404));
    }

    @ExceptionHandler(InvalidParameterFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleFormatError(InvalidParameterFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Invalid Format", e.getMessage(), 400));
    }

    @ExceptionHandler(InvalidBookingRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBookingError(InvalidBookingRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Booking Error", e.getMessage(), 400));
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<ErrorResponseDTO> handleDatabaseError(DatabaseConnectionException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponseDTO("Service Unavailable", e.getMessage(), 503));
    }

    @ExceptionHandler(MissingRequiredParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingParam(MissingRequiredParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Missing Data", e.getMessage(), 400));
    }
}