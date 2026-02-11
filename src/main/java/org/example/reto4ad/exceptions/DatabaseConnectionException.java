package org.example.reto4ad.exceptions;

/**
 * Excepción lanzada cuando ocurre un fallo crítico de conexión con MongoDB.
 * Se utiliza para notificar problemas de disponibilidad del servicio de datos.
 */
public class DatabaseConnectionException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje predefinido de error crítico.
     */
    public DatabaseConnectionException() {
        super("A critical error occurred while connecting to the database.");
    }
}