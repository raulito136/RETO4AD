package org.example.reto4ad.security;

import org.example.reto4ad.entities.UsuarioDB;
import org.example.reto4ad.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio personalizado para la carga de detalles de usuario.
 * Implementa UserDetailsService para permitir que Spring Security
 * valide las credenciales consultando la colección de usuarios en MongoDB.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    /**
     * Constructor para la inyección del repositorio de usuarios.
     * @param usuarioRepository Repositorio que gestiona la persistencia de UsuarioDB.
     */
    public AppUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Busca un usuario en la base de datos por su nombre de usuario y construye
     * un objeto UserDetails compatible con Spring Security.
     * * @param username El nombre de usuario introducido en el login.
     * @return UserDetails con el nombre, contraseña y roles del usuario.
     * @throws UsernameNotFoundException Si el usuario no existe en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioDB> usuarioActual = usuarioRepository.findUsuarioDBByUsername(username);

        if(usuarioActual.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        UserDetails user = User.withUsername(username)
                .password("{noop}" + usuarioActual.get().getPassword())
                .roles("ADMIN")
                .build();
        return user;
    }
}