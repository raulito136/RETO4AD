package org.example.reto4ad.exceptions;

public class MalformedJsonException extends RuntimeException {
    public MalformedJsonException() {
        super("The request body contains an invalid or malformed JSON.");
    }
}