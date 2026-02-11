package org.example.reto4ad.repository;

import org.example.reto4ad.entities.UsuarioDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de acceso a datos para la entidad UsuarioDB.
 * Se encarga de las operaciones de persistencia relacionadas con la gestión
 * de usuarios y credenciales en MongoDB.
 */
@Repository
public interface UsuarioRepository extends MongoRepository<UsuarioDB, String> {

    /**
     * Busca un usuario en la base de datos utilizando su nombre de usuario.
     * Este método es comúnmente utilizado durante el proceso de login para
     * recuperar las credenciales.
     * * @param username El nombre de usuario único.
     * @return Un Optional con el usuario encontrado, o vacío en caso contrario.
     */
    Optional<UsuarioDB> findUsuarioDBByUsername(String username);
}