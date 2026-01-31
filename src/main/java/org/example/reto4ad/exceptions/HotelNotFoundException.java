package org.example.reto4ad.exceptions;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException(String id) {
        super("El hotel con ID " + id + " no ha sido encontrado en nuestro sistema.");
    }
}
