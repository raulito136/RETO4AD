package org.example.reto4ad.exceptions;

public class MissingRequiredParameterException extends RuntimeException {
    public MissingRequiredParameterException(String parameter) {
        super("The required parameter '" + parameter + "' is missing.");
    }
}