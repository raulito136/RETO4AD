package org.example.reto4ad.repository;

import org.example.reto4ad.entities.UsuarioDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<UsuarioDB, String> {

    Optional<UsuarioDB> findUsuarioDBByUsername(String username);
}
