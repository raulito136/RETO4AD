package org.example.reto4ad.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad que representa a un usuario del sistema en MongoDB.
 * Mapeada a la colección "users".
 */
@Data
@Document(collection = "users")
public class UsuarioDB {

    /** Identificador único del usuario en MongoDB. */
    @Id
    private String _id;

    /** Nombre de usuario para el inicio de sesión. */
    private String username;

    /** Contraseña del usuario. */
    private String password;
}