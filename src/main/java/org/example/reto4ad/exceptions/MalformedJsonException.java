package org.example.reto4ad.exceptions;

/**
 * Excepción lanzada cuando el cuerpo de la solicitud no es un JSON válido.
 */
public class MalformedJsonException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje de JSON mal formado.
     */
    public MalformedJsonException() {
        super("The request body contains an invalid or malformed JSON.");
    }
}