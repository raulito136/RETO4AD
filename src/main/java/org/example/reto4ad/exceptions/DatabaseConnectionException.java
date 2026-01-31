package org.example.reto4ad.exceptions;

public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException() {
        super("A critical error occurred while connecting to the database.");
    }
}