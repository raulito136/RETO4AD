package org.example.reto4ad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "hoteles_espana")
public class Hotel {

    @Id
    private String id;

    private String nombre;

    private Double calificacion;

    private String ubicacion;

    private Integer precioPorNoche;

    private Integer estrellas;
}