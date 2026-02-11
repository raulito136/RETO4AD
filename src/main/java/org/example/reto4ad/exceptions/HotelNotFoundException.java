package org.example.reto4ad.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un hotel en el sistema.
 * Generalmente, se utiliza en búsquedas por ID o por nombre donde el
 * resultado es nulo o vacío.
 */
public class HotelNotFoundException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje personalizado basado en el ID.
     * @param id Identificador o nombre del hotel que no se pudo encontrar.
     */
    public HotelNotFoundException(String id) {
        super("El hotel con ID " + id + " no ha sido encontrado en nuestro sistema.");
    }
}