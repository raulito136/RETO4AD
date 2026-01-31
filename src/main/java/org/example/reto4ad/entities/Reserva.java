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

    // Getters y Setters manuales para evitar fallos de Lombok
    public String getHotelId() { return hotelId; }
    public void setHotelId(String hotelId) { this.hotelId = hotelId; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public String getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(String fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public Integer getNoches() { return noches; }
    public void setNoches(Integer noches) { this.noches = noches; }
}