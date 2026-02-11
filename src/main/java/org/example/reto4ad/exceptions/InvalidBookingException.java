package org.example.reto4ad.exceptions;

/**
 * Excepción base para errores generales en el proceso de reserva.
 */
public class InvalidBookingException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje específico.
     * @param message Motivo por el cual la reserva es inválida.
     */
    public InvalidBookingException(String message) {
        super(message);
    }
}