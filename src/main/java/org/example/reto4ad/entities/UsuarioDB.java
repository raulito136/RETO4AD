package org.example.reto4ad.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class UsuarioDB {
    @Id
    private String _id;
    private String username;
    private String password;
}
