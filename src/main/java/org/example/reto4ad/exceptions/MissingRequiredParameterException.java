package org.example.reto4ad.exceptions;

/**
 * Excepción lanzada cuando falta un parámetro obligatorio en la solicitud.
 * Se utiliza para validar que campos como 'ubicacion' u 'hotelId' estén presentes.
 */
public class MissingRequiredParameterException extends RuntimeException {

    /**
     * Construye la excepción indicando qué parámetro falta.
     * @param parameter Nombre del parámetro ausente.
     */
    public MissingRequiredParameterException(String parameter) {
        super("El parametro requerido '" + parameter + "' no esta.");
    }
}