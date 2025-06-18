package com.gym.fit_power.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info=@Info(
                title = "Fit Power Gym App",
                description = "API para manejar la App Fit Power",
                termsOfService = "www.gym-fitpower.com/terminos-de-servicio",
                version = "1.0.0",
                contact = @Contact(
                        name = "Equipo de contacto",
                        url = "www.gym-fitpower.com/contacto",
                        email = "contacto@fit-power.com"
                ),
                license = @License(
                        name = "Standard License",
                        url = "www.gym-fitpower.com/licencia"
                )
        ),
        servers = {
                @Server(
                        description = "Dev Server",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Prod Server",
                        url = "http://localhost:8081"
                )
        },
        security = @SecurityRequirement(
                name = "Security Token"
        )
)
@SecurityScheme(
        name = "Security Token",
        description = "JWT auth description",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
