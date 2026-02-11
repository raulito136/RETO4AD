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
                .body(new ErrorResponseDTO("Hotel no encontrado", e.getMessage(), 404));
    }

    @ExceptionHandler(InvalidParameterFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleFormatError(InvalidParameterFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Formato invalido", e.getMessage(), 400));
    }

    @ExceptionHandler(InvalidBookingRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBookingError(InvalidBookingRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Error en Reserva", e.getMessage(), 400));
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<ErrorResponseDTO> handleDatabaseError(DatabaseConnectionException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponseDTO("Servicio de base de datos no disponible", e.getMessage(), 503));
    }

    @ExceptionHandler(MissingRequiredParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingParam(MissingRequiredParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Faltan datos requeridos", e.getMessage(), 400));
    }
}