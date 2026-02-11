package org.example.reto4ad.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de la documentación OpenAPI (Swagger).
 * Configura la interfaz de Swagger para incluir el botón "Authorize" y permitir
 * el envío de credenciales mediante HTTP Basic Auth.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Define la configuración personalizada de OpenAPI.
     * Registra un esquema de seguridad de tipo HTTP Basic para que la API
     * documentada pueda ser probada con usuarios autenticados.
     * * @return Una instancia configurada de OpenAPI.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "basicAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                        .scheme("basic")));
    }
}