package org.example.reto4ad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de Transferencia de Datos (DTO) para representar respuestas de error.
 * Se utiliza para encapsular la información de una excepción y enviarla
 * de forma estructurada al cliente en formato JSON.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {

    /** Nombre o tipo breve del error (ej. "Hotel no encontrado"). */
    private String error;

    /** Mensaje detallado que explica la causa del error. */
    private String message;

    /** Código de error personalizado o estado HTTP. */
    private Integer errorCode;

}