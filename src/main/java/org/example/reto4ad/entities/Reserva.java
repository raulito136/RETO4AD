package org.example.reto4ad.entities;

import lombok.Data;

/**
 * Modelo para representar la solicitud de una reserva en la API.
 * Contiene la información necesaria para vincular a un cliente con un hotel
 * durante un periodo determinado.
 */
@Data
public class Reserva {

    /** Identificador del hotel al que pertenece la reserva. */
    private String hotelId;

    /** Nombre completo del cliente que realiza la reserva. */
    private String nombreCliente;

    /** Fecha de inicio de la estancia (formato String). */
    private String fechaEntrada;

    /** Cantidad total de noches de la estancia. */
    private Integer noches;
}