package org.example.reto4ad.entities;

import lombok.Data;

/**
 * Modelo para representar una reserva en la API.
 */

@Data
public class Reserva {
    private String hotelId;
    private String nombreCliente;
    private String fechaEntrada;
    private Integer noches;
}