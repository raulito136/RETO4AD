package org.example.reto4ad.security;

import org.example.reto4ad.entities.UsuarioDB;
import org.example.reto4ad.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private UsuarioRepository usuarioRepository;

    public AppUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioDB> usuarioActual=usuarioRepository.findUsuarioDBByUsername(username);

        if(usuarioActual.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        UserDetails user= User.withUsername(username).password("{noop}"+usuarioActual.get().getPassword()).roles("ADMIN").build();
        return user;
    }
}
