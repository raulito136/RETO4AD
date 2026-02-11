package org.example.reto4ad.exceptions;

/**
 * Excepción lanzada cuando los datos de una solicitud de reserva son incorrectos.
 * Por ejemplo, si el número de noches es menor o igual a cero.
 */
public class InvalidBookingRequestException extends RuntimeException {

    /**
     * Construye la excepción con detalles sobre el error en la reserva.
     * @param detail Descripción específica del fallo en los datos.
     */
    public InvalidBookingRequestException(String detail) {
        super(detail);
    }
}