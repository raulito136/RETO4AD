package org.example.reto4ad.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad que representa un Hotel en la base de datos MongoDB.
 * Mapeada a la colección "hoteles_espana".
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "hoteles_espana")
public class Hotel {

    /** Identificador único generado por MongoDB. */
    @Id
    private String id;

    /** Nombre comercial del hotel. */
    private String nombre;

    /** Calificación promedio de los usuarios (ej. 4.5). */
    private Double calificacion;

    /** Dirección o zona geográfica donde se ubica el hotel. */
    private String ubicacion;

    /** Coste de la estancia por una sola noche. */
    private Integer precioPorNoche;

    /** Categoría del hotel expresada en estrellas (1-5). */
    private Integer estrellas;
}