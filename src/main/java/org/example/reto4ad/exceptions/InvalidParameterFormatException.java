package org.example.reto4ad.exceptions;

/**
 * Excepción lanzada cuando un parámetro tiene un formato no válido.
 * Por ejemplo, enviar texto en un campo que requiere un número decimal.
 */
public class InvalidParameterFormatException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje explicativo.
     * @param message Descripción del error de formato.
     */
    public InvalidParameterFormatException(String message) {
        super(message);
    }
}