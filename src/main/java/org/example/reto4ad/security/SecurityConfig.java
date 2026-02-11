package org.example.reto4ad.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.example.reto4ad.dto.ErrorResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración principal de seguridad de la aplicación.
 * Define las políticas de autorización, el manejo de sesiones, la seguridad de las rutas
 * y la integración de la autenticación básica y mediante formulario.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad (SecurityFilterChain).
     * - Desactiva CSRF para facilitar el uso de la API REST.
     * - Define rutas públicas (Swagger, recursos estáticos, listado de hoteles).
     * - Obliga a estar autenticado para cualquier otra petición.
     * * @param http Objeto de configuración de seguridad HTTP.
     * @return El filtro de seguridad configurado.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permite acceso sin login a la documentación de API
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Permite acceso a recursos estáticos del frontend
                        .requestMatchers("/", "/index.html", "/css/**", "/javascript/**", "/favicon.ico", "/error").permitAll()
                        // Permite ver hoteles y detalles sin estar logueado
                        .requestMatchers(HttpMethod.GET, "/hoteles").permitAll()
                        .requestMatchers(HttpMethod.GET, "/hoteles/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/hoteles/buscar").permitAll()
                        // Cualquier otra operación (POST, PUT, DELETE) requiere autenticación
                        .anyRequest().authenticated()
                )
                .httpBasic( (basic) -> {
                    basic.authenticationEntryPoint(customAuthenticationEntryPoint());
                })
                .formLogin(form -> form
                        .loginPage("/index.html")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acceso Denegado");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .permitAll()
                );

        return http.build();
    }

    /**
     * Define un punto de entrada personalizado para capturar errores de autenticación.
     * Devuelve una respuesta en formato JSON utilizando el ErrorResponseDTO
     * en lugar del error por defecto de Spring.
     * * @return Una instancia de AuthenticationEntryPoint.
     */
    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, e) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ErrorResponseDTO error = new ErrorResponseDTO(
                    "Usuario no autorizado",
                    "El usuario y contraseña no coinciden",
                    401
            );
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(error));
        };
    }
}