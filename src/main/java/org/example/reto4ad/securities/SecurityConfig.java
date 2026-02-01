package org.example.reto4ad.securities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Necesario para que funcionen POST/PUT/DELETE desde JS
                .authorizeHttpRequests(auth -> auth
                        // 1. Permitir la interfaz de usuario y recursos estáticos
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/css/**",
                                "/javascript/**",
                                "/static/**",
                                "/favicon.ico",
                                "/error"
                        ).permitAll()

                        // 2. Endpoints Públicos de la API (Consulta)
                        .requestMatchers(HttpMethod.GET, "/hoteles").permitAll()
                        .requestMatchers(HttpMethod.GET, "/hoteles/{id}").permitAll()

                        // 3. Todo lo demás (POST, PUT, DELETE, búsquedas filtradas) requiere login
                        .anyRequest().authenticated()
                )
                // Usamos Basic Auth para que el navegador muestre el cuadro de login
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}