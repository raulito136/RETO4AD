package org.example.reto4ad.exceptions;

public class InvalidBookingRequestException extends RuntimeException {
    public InvalidBookingRequestException(String detail) {
        super("Invalid booking data: " + detail);
    }
}