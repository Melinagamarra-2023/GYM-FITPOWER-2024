package com.gym.fit_power.controller;

import com.gym.fit_power.dto.request.RoutineRequestDto;
import com.gym.fit_power.dto.request.TrainingDiaryRequestDto;
import com.gym.fit_power.dto.response.RoutineResponseDto;
import com.gym.fit_power.dto.response.TrainingDiaryResponseDto;
import com.gym.fit_power.service.RoutineService;
import com.gym.fit_power.service.TrainingDiaryService;
import com.gym.fit_power.util.DecodeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/workout")
@Tag(name = "Workout Controller", description = "Controller for managing workouts")
public class WorkoutController {

    private final RoutineService routineService;
    private final TrainingDiaryService trainingDiaryService;

    // El entrenador revisa las rutinas de un cliente
    @GetMapping("/routines/{clientCuit}")
    @PreAuthorize("hasAnyRole('TRAINER')")
    @Operation(
            summary = "Visualizar las rutinas de un cliente (Entrenador)",
            description = "Permite a un entrenador obtener una lista de todas las rutinas asociadas a un cliente específico, " +
                    "identificado por su CUIT. Se requiere rol TRAINER.",
            parameters = {
                    @Parameter(
                            name = "clientCuit",
                            description = "CUIT del cliente cuyas rutinas se desean obtener.",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", example = "20304050607")
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de rutinas del cliente obtenida exitosamente. Retorna una lista vacía si el cliente no tiene rutinas o si el CUIT del cliente no corresponde a un cliente existente.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoutineResponseDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autorizado. El token de autenticación es inválido o no ha sido proporcionado.",
                    content = @Content(mediaType = "application/json") // Asume una respuesta de error genérica de Spring Security
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Prohibido. El usuario autenticado no posee el rol TRAINER.",
                    content = @Content(mediaType = "application/json") // Asume una respuesta de error genérica de Spring Security
            )
    })
    public ResponseEntity<List<RoutineResponseDto>> viewAClientRoutines(@PathVariable String clientCuit) {
        return new ResponseEntity<>(routineService.findByClient(clientCuit), HttpStatus.OK);
    }

    // El entrenador revisa la rutina activa de un cliente
    @GetMapping("/routine/{clientCuit}")
    @PreAuthorize("hasAnyRole('TRAINER')")
    @Operation(
            summary = "Visualizar la rutina activa de un cliente (Entrenador)",
            description = "Permite a un entrenador obtener la rutina activa de un cliente específico, " +
                    "identificado por su CUIT. Se requiere rol TRAINER. " +
                    "Si el cliente no existe o no tiene una rutina activa, la respuesta puede ser un cuerpo vacío o nulo con estado 200 OK, " +
                    "dependiendo de la implementación del servicio.",
            parameters = {
                    @Parameter(
                            name = "clientCuit",
                            description = "CUIT del cliente cuya rutina activa se desea obtener.",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", example = "20304050607")
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rutina activa del cliente obtenida exitosamente. " +
                            "El cuerpo de la respuesta puede ser nulo si el cliente no tiene una rutina activa o si el cliente no existe.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoutineResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autorizado. El token de autenticación es inválido o no ha sido proporcionado.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Prohibido. El usuario autenticado no posee el rol TRAINER.",
                    content = @Content(mediaType = "application/json")
            )
            // Podrías considerar un 404 si el servicio lanza una excepción específica para "cliente no encontrado"
            // o "rutina activa no encontrada" y tienes un ExceptionHandler global que lo mapee a 404.
            // Por ahora, sigo la lógica de que un 200 con cuerpo nulo cubre estos casos.
    })
    public ResponseEntity<RoutineResponseDto> viewAClientActiveRoutine(@PathVariable String clientCuit) {
        return new ResponseEntity<>(routineService.findClientActiveRoutine(clientCuit), HttpStatus.OK);
    }

    // El entrenador crea una rutina para un cliente
    @PostMapping("/routine")
    @PreAuthorize("hasAnyRole('TRAINER')")
    @Operation(
            summary = "Crear una rutina para un cliente (Entrenador)",
            description = "Permite a un entrenador crear una nueva rutina para un cliente. " +
                    "El CUIT del entrenador se extrae del token de autenticación. " +
                    "El CUIT del cliente debe ser proporcionado en el cuerpo de la solicitud. " +
                    "Se requiere rol TRAINER.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la rutina a crear. Debe incluir el CUIT del cliente (`clientCuit`) y los detalles de la rutina.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoutineRequestDto.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Rutina creada exitosamente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoutineResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida. Verifique los datos enviados en el cuerpo de la solicitud (ej. campos faltantes o con formato incorrecto).",
                    content = @Content(mediaType = "application/json") // Asume una respuesta de error genérica para validaciones
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autorizado. El token de autenticación es inválido o no ha sido proporcionado.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Prohibido. El usuario autenticado no posee el rol TRAINER.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado. El CUIT del cliente proporcionado en la solicitud no corresponde a un cliente existente.",
                    content = @Content(mediaType = "application/json") // Asume que el servicio puede generar un error que se mapea a 404
            )
            // Considerar @ApiResponse para 409 Conflict si hay restricciones de unicidad que puedan ser violadas.
            // Considerar @ApiResponse para 500 Internal Server Error para errores inesperados.
    })
    public ResponseEntity<RoutineResponseDto> createARoutineForAClient(@Valid @RequestBody RoutineRequestDto request,
                                                                       @RequestHeader String authorization) {
        String trainerCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(routineService.save(request, trainerCuit), HttpStatus.CREATED);
    }

    // <<<<<<<<<<<<<<<< TRAINING-DIARY >>>>>>>>>>>>>>>> //

    // El cliente agrega un diario de entrenamiento
    @PostMapping("/training")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(
            summary = "Agregar un diario de entrenamiento (Cliente/Usuario)",
            description = "Permite a un cliente (rol USER) agregar una nueva entrada a su diario de entrenamiento. " +
                    "El CUIT del cliente se extrae automáticamente del token de autenticación. " +
                    "La entrada del diario se asocia a la rutina activa del cliente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del diario de entrenamiento a agregar. Debe incluir los detalles del entrenamiento realizado (ej. fecha, ejercicios, series, repeticiones, notas). " +
                            "El sistema lo asociará a la rutina activa del cliente.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainingDiaryRequestDto.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Diario de entrenamiento agregado exitosamente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainingDiaryResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida. Verifique los datos enviados. " +
                            "Esto puede ocurrir si faltan campos obligatorios, los datos tienen un formato incorrecto, " +
                            "o si el cliente no tiene una rutina activa a la cual asociar el diario.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autorizado. El token de autenticación es inválido o no ha sido proporcionado.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Prohibido. El usuario autenticado no posee el rol USER.",
                    content = @Content(mediaType = "application/json")
            )
            // Considerar un 404 si el servicio específicamente indica "cliente no encontrado" a partir del token,
            // aunque esto es menos probable si el token es válido y el usuario existe.
            // El caso de "rutina activa no encontrada" se maneja como un 400 según la descripción.
    })
    public ResponseEntity<TrainingDiaryResponseDto> addTrainingDiaryForAClient(@Valid @RequestBody TrainingDiaryRequestDto request,
                                                                               @RequestHeader String authorization) {
        String clientCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(trainingDiaryService.add(request, clientCuit),HttpStatus.CREATED);
    }

    // El cliente revisa su diario de entrenamiento
    @GetMapping("/training")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(
            summary = "Visualizar el diario de entrenamiento del cliente (Usuario)",
            description = "Permite a un cliente (rol USER) obtener una lista de todas las entradas de su diario de entrenamiento " +
                    "asociadas a su rutina activa. El CUIT del cliente se extrae del token de autenticación. " +
                    "Se requiere rol USER."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de entradas del diario de entrenamiento obtenida exitosamente. " +
                            "Retorna una lista vacía si el cliente no tiene entradas en el diario para su rutina activa, " +
                            "o si no tiene una rutina activa.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrainingDiaryResponseDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autorizado. El token de autenticación es inválido o no ha sido proporcionado.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Prohibido. El usuario autenticado no posee el rol USER.",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<List<TrainingDiaryResponseDto>> viewTrainingDiaryOfAClientRoutine(@RequestHeader String authorization){
        String clientCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(trainingDiaryService.readByClientActiveRoutine(clientCuit), HttpStatus.OK);
    }

    // El cliente revisa su rutina activa
    @GetMapping("/routine")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(
            summary = "Visualizar la rutina activa del cliente (Usuario)",
            description = "Permite a un cliente (rol USER) obtener su rutina activa. " +
                    "El CUIT del cliente se extrae automáticamente del token de autenticación. Se requiere rol USER. " +
                    "Si el cliente no tiene una rutina activa, la respuesta puede ser un cuerpo nulo con estado 200 OK."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rutina activa del cliente obtenida exitosamente. " +
                            "El cuerpo de la respuesta puede ser nulo si el cliente no tiene una rutina activa.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoutineResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autorizado. El token de autenticación es inválido o no ha sido proporcionado.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Prohibido. El usuario autenticado no posee el rol USER.",
                    content = @Content(mediaType = "application/json")
            )
            // Al igual que con viewAClientActiveRoutine, un 404 podría ser una opción si el servicio
            // específicamente lanza una excepción para "rutina activa no encontrada" y se maneja globalmente.
            // Por ahora, se asume que un 200 con cuerpo nulo es el comportamiento esperado.
    })
    public ResponseEntity<RoutineResponseDto> viewClientActiveRoutine(@RequestHeader String authorization){
        String clientCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(routineService.findClientActiveRoutine(clientCuit), HttpStatus.OK);
    }

}
