package com.gym.fit_power.controller;

import com.gym.fit_power.dto.NutritionPlanDTO;
import com.gym.fit_power.dto.NutritionDiaryDTO;
import com.gym.fit_power.service.NutritionPlanService;
import com.gym.fit_power.service.NutritionDiaryService;
import com.gym.fit_power.service.impl.NutriPlanServiceImpl;
import com.gym.fit_power.util.DecodeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gym.fit_power.constant.NutritinistConstants.CREATE_NUTRIPLAN;

@Slf4j
@Validated
@RestController
@RequestMapping("api/v1/NutritionPlan")
@Tag(name = "Nutrition Plans", description = "Gestión de planes nutricionales y sus diarios asociados")

public class NutritionPlanController {

    private final NutritionPlanService service;
    private final NutritionDiaryService nutritionDiaryService;
    protected static final Logger logger = LoggerFactory.getLogger(NutritionPlanController.class);

    public NutritionPlanController(NutriPlanServiceImpl service, NutritionDiaryService nutritionDiaryService) {
        this.service = service;
        this.nutritionDiaryService = nutritionDiaryService;
    }

    @PostMapping("/savePlan")
    //@PreAuthorize("hasAnyRole('ADMIN', 'NUTRITIONIST')")
    @Operation(
            summary = "Crear un plan de nutrición",
            description = "Permite a un usuario con rol **ADMIN** o **NUTRITIONIST** crear un nuevo plan nutricional para un cliente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo plan nutricional",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NutritionPlanDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Plan nutricional creado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NutritionPlanDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ya existe un plan con el mismo ID o los datos son inválidos",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "El usuario no tiene permisos para realizar esta acción",
                            content = @Content
                    )
            }
    )

    public ResponseEntity<NutritionPlanDTO> saveNutriPlan(@Valid @RequestBody NutritionPlanDTO request) {
        logger.info("{} para el cliente {}", request.getClientCuit(), CREATE_NUTRIPLAN);
        if (service.readOne(request.getId()) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        NutritionPlanDTO response = service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // --------------------------------------------------------
    @Operation(
            summary = "Obtener todos los planes nutricionales del cliente autenticado",
            description = "Devuelve una lista con todos los planes nutricionales asociados al cliente identificado en el token JWT.",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT del cliente en formato 'Bearer <token>'",
                            required = true,
                            in = ParameterIn.HEADER,
                            schema = @Schema(type = "string", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de planes nutricionales encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NutritionPlanDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token inválido o expirado",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado",
                            content = @Content
                    )
            }
    )
    @GetMapping("/nutrition_plans")
    public ResponseEntity<List<NutritionPlanDTO>> viewNutritionPlans(@RequestHeader String authorization) {
        String clientCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(service.readByClient(clientCuit), HttpStatus.OK);
    }

    // --------------------------------------------------------
    @Operation(
            summary = "Obtener el plan nutricional activo del cliente autenticado",
            description = "Retorna el plan nutricional actualmente activo del cliente identificado mediante el token JWT.",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT del cliente en formato 'Bearer <token>'",
                            required = true,
                            in = ParameterIn.HEADER,
                            schema = @Schema(type = "string", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Plan nutricional activo encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NutritionPlanDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token inválido o expirado",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe un plan activo o el cliente no fue encontrado",
                            content = @Content
                    )
            }
    )
    @GetMapping("/nutrition_plans/active")
    public ResponseEntity<NutritionPlanDTO> viewActivePlan(
            @RequestHeader String authorization) {
        String clientCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(service.readPlanActiveByClient(clientCuit), HttpStatus.OK);
    }

    // --------------------------------------------------------
    @Operation(
            summary = "Obtener un plan nutricional específico del cliente autenticado",
            description = "Devuelve un plan nutricional específico (por ID) del cliente autenticado identificado en el token JWT.",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT del cliente en formato 'Bearer <token>'",
                            required = true,
                            in = ParameterIn.HEADER,
                            schema = @Schema(type = "string", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...")
                    ),
                    @Parameter(
                            name = "id",
                            description = "ID del plan nutricional a consultar",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "5")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Plan nutricional encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NutritionPlanDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token inválido o expirado",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Plan no encontrado o no pertenece al cliente autenticado",
                            content = @Content
                    )
            }
    )
    @GetMapping("/nutrition_plans/{id}")
    public ResponseEntity<NutritionPlanDTO> viewOnePlan(
            @RequestHeader String authorization,
            @PathVariable Long id) {
        String clientCuit =  DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(service.readPlanByClient(clientCuit, id), HttpStatus.OK);
    }

    // --------------------------------------------------------
    @Operation(
            summary = "Crear o actualizar el diario nutricional del plan activo del cliente autenticado",
            description = "Permite al cliente autenticado registrar o modificar las entradas de su diario nutricional asociado al plan activo. El CUIT se obtiene del token JWT.",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT del cliente en formato 'Bearer <token>'",
                            required = true,
                            in = ParameterIn.HEADER,
                            schema = @Schema(type = "string", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...")
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del diario nutricional a crear o actualizar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NutritionDiaryDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Diario nutricional actualizado o creado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NutritionDiaryDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos o inconsistencia en la solicitud",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token inválido o expirado",
                            content = @Content
                    )
            }
    )
    @PutMapping("/nutrition_plans/active/diary")
    public ResponseEntity<NutritionDiaryDTO> updateOrCreateNutritionDiary(
            @RequestHeader String authorization,
            @RequestBody NutritionDiaryDTO request) {
        String clientCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(nutritionDiaryService.update(clientCuit, request), HttpStatus.OK);
    }

    // --------------------------------------------------------
    @Operation(
            summary = "Ver el diario nutricional de un plan específico",
            description = "Devuelve todas las entradas del diario nutricional asociadas a un plan en particular del cliente autenticado. El CUIT se obtiene del token JWT.",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT del cliente en formato 'Bearer <token>'",
                            required = true,
                            in = ParameterIn.HEADER,
                            schema = @Schema(type = "string", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...")
                    ),
                    @Parameter(
                            name = "id",
                            description = "ID del plan nutricional a consultar",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "12")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entradas del diario encontradas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NutritionDiaryDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró el plan o el diario",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token inválido o expirado",
                            content = @Content
                    )
            }
    )
    @GetMapping("/nutrition_plans/{id}/diary")
    public ResponseEntity<List<NutritionDiaryDTO>> viewNutriPlanDiary(
            @RequestHeader String authorization,
            @PathVariable(value = "id") Long id) {

        String clientCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(nutritionDiaryService.readByNutritionPlan(clientCuit, id), HttpStatus.OK);
    }


    // --------------------------------------------------------
    @Operation(
            summary = "Ver el diario nutricional del plan activo",
            description = "Retorna todas las entradas del diario asociadas al plan nutricional actualmente activo del cliente autenticado. El CUIT se obtiene del token JWT.",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Token JWT del cliente en formato 'Bearer <token>'",
                            required = true,
                            in = ParameterIn.HEADER,
                            schema = @Schema(type = "string", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entradas del diario del plan activo encontradas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NutritionDiaryDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente o plan activo no encontrado",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token inválido o expirado",
                            content = @Content
                    )
            }
    )
    @GetMapping("/nutrition_plans/active/diary")
    public ResponseEntity<List<NutritionDiaryDTO>> viewActivePlanDiary(
            @RequestHeader String authorization) {

        String clientCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(nutritionDiaryService.readByClientActivePlan(clientCuit), HttpStatus.OK);
    }


}
