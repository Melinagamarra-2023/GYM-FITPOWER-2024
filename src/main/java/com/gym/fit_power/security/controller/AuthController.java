package com.gym.fit_power.security.controller;

import com.gym.fit_power.security.dto.AuthResponse;
import com.gym.fit_power.security.dto.LoginRequest;
import com.gym.fit_power.security.dto.RegisterRequest;
import com.gym.fit_power.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication Controller", description = "Controller for user registration and login")
public class AuthController {
    private final AuthService service;

    @PostMapping("register")
    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Permite a un nuevo usuario registrarse en el sistema proporcionando sus datos personales y credenciales.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos necesarios para el registro del usuario.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequest.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", // Aunque tu servicio devuelve OK, para un registro exitoso es más común 201 Created.
                    // Pero sigo tu implementación actual.
                    description = "Usuario registrado exitosamente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "object", example = "{\"message\": \"User registered successfully!\"}")
                            // Alternativamente, podrías definir un DTO simple para esta respuesta.
                            // examples = @ExampleObject(value = "{\"message\": \"User registered successfully!\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida. Esto puede ocurrir si los datos proporcionados no son válidos " +
                            "(ej. CUIT ya existe, email con formato incorrecto, campos obligatorios faltantes).",
                    content = @Content(mediaType = "application/json") // Asume una respuesta de error genérica
            )
            // Considerar 409 Conflict si el CUIT o email ya existen y quieres ser más específico.
    })
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        service.register(registerRequest);
        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }

    @PostMapping("login")
    @Operation(
            summary = "Iniciar sesión de un usuario",
            description = "Permite a un usuario existente iniciar sesión proporcionando su CUIT y contraseña para obtener un token de autenticación.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales del usuario para iniciar sesión.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequest.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Inicio de sesión exitoso. Retorna un token de autenticación.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida. Verifique los datos enviados (ej. campos faltantes o formato incorrecto).",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autorizado. Credenciales inválidas (CUIT o contraseña incorrectos).",
                    content = @Content(mediaType = "application/json") // Asume una respuesta de error genérica de Spring Security o tu AuthSevice
            )
    })
    public ResponseEntity<AuthResponse> login (@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(service.login(loginRequest));
    }


}
