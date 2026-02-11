package org.example.reto4ad.controllers;

import org.example.reto4ad.dto.ErrorResponseDTO;
import org.example.reto4ad.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para los controladores de la aplicación.
 * Captura las excepciones lanzadas por los controladores y las transforma en
 * respuestas JSON estandarizadas usando ErrorResponseDTO.
 */
@RestControllerAdvice
public class HotelControllerAdvice {

    /**
     * Maneja el caso cuando un recurso de tipo Hotel no es encontrado.
     * @param e Excepción de tipo HotelNotFoundException.
     * @return Respuesta con estado 404 (Not Found).
     */
    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleHotelNotFound(HotelNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO("Hotel no encontrado", e.getMessage(), 404));
    }

    /**
     * Maneja errores de formato en los parámetros de entrada.
     * @param e Excepción de tipo InvalidParameterFormatException.
     * @return Respuesta con estado 400 (Bad Request).
     */
    @ExceptionHandler(InvalidParameterFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleFormatError(InvalidParameterFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Formato invalido", e.getMessage(), 400));
    }

    /**
     * Maneja errores relacionados con la lógica de negocio de las reservas.
     * @param e Excepción de tipo InvalidBookingRequestException.
     * @return Respuesta con estado 400 (Bad Request).
     */
    @ExceptionHandler(InvalidBookingRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBookingError(InvalidBookingRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Error en Reserva", e.getMessage(), 400));
    }

    /**
     * Maneja fallos en la conexión con el servidor de base de datos.
     * @param e Excepción de tipo DatabaseConnectionException.
     * @return Respuesta con estado 503 (Service Unavailable).
     */
    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<ErrorResponseDTO> handleDatabaseError(DatabaseConnectionException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponseDTO("Servicio de base de datos no disponible", e.getMessage(), 503));
    }

    /**
     * Maneja el caso de ausencia de parámetros obligatorios en la petición.
     * @param e Excepción de tipo MissingRequiredParameterException.
     * @return Respuesta con estado 400 (Bad Request).
     */
    @ExceptionHandler(MissingRequiredParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingParam(MissingRequiredParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Faltan datos requeridos", e.getMessage(), 400));
    }
}