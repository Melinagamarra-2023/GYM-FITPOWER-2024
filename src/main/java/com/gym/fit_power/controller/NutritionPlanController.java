package com.gym.fit_power.controller;

import com.gym.fit_power.dto.NutritionPlanDTO;
import com.gym.fit_power.dto.NutritionDiaryDTO;
import com.gym.fit_power.service.NutritionPlanService;
import com.gym.fit_power.service.NutritionDiaryService;
import com.gym.fit_power.service.impl.NutriPlanServiceImpl;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'NUTRITIONIST')")
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
            summary = "Obtener todos los planes nutricionales de un cliente",
            description = "Devuelve una lista con todos los planes nutricionales asociados al cliente identificado por su CUIT.",
            parameters = {
                    @Parameter(name = "cuit", description = "CUIT del cliente", example = "20123456789")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de planes nutricionales encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionPlanDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
            }
    )
    @GetMapping("/{cuit}/nutrition_plans")
    public ResponseEntity<List<NutritionPlanDTO>> viewNutritionPlans(@PathVariable(value = "cuit") String clientCuit) {
        return new ResponseEntity<>(service.readByClient(clientCuit), HttpStatus.OK);
    }

    // --------------------------------------------------------
    @Operation(
            summary = "Obtener el plan nutricional activo de un cliente",
            description = "Retorna el plan nutricional actualmente activo del cliente identificado por su CUIT.",
            parameters = {
                    @Parameter(name = "cuit", description = "CUIT del cliente", example = "20123456789")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Plan nutricional activo encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionPlanDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No existe un plan activo o cliente no encontrado", content = @Content)
            }
    )
    @GetMapping("/{cuit}/nutrition_plans/active")
    public ResponseEntity<NutritionPlanDTO> viewActivePlan(@PathVariable(value = "cuit") String clientCuit) {
        return new ResponseEntity<>(service.readPlanActiveByClient(clientCuit), HttpStatus.OK);
    }

    // --------------------------------------------------------
    @Operation(
            summary = "Obtener un plan nutricional específico de un cliente",
            description = "Permite consultar un plan nutricional específico mediante su ID para un cliente determinado.",
            parameters = {
                    @Parameter(name = "cuit", description = "CUIT del cliente", example = "20123456789"),
                    @Parameter(name = "id", description = "ID del plan nutricional", example = "12")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Plan nutricional encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionPlanDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Plan o cliente no encontrado", content = @Content)
            }
    )
    @GetMapping("/{cuit}/nutrition_plans/{id}")
    public ResponseEntity<NutritionPlanDTO> viewOnePlan(@PathVariable(value = "cuit") String clientCuit,
                                                        @PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(service.readPlanByClient(clientCuit, id), HttpStatus.OK);
    }

    // --------------------------------------------------------
    @Operation(
            summary = "Actualizar o crear un diario nutricional para el plan activo",
            description = "Permite crear o actualizar las entradas del diario nutricional asociado al plan activo del cliente.",
            parameters = {
                    @Parameter(name = "cuit", description = "CUIT del cliente", example = "20123456789")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del diario nutricional a crear o actualizar",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionDiaryDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Diario nutricional actualizado o creado correctamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionDiaryDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos o inconsistencia en la solicitud", content = @Content)
            }
    )
    @PutMapping("/{cuit}/nutrition_plans/active/diary")
    public ResponseEntity<NutritionDiaryDTO> updateOrCreateNutritionDiary(
            @PathVariable(value = "cuit") String clientCuit,
            @RequestBody NutritionDiaryDTO request) {
        return new ResponseEntity<>(nutritionDiaryService.update(clientCuit, request), HttpStatus.OK);
    }

    // --------------------------------------------------------
    @Operation(
            summary = "Ver el diario nutricional de un plan específico",
            description = "Devuelve todas las entradas del diario nutricional asociadas a un plan en particular del cliente.",
            parameters = {
                    @Parameter(name = "cuit", description = "CUIT del cliente", example = "20123456789"),
                    @Parameter(name = "id", description = "ID del plan nutricional", example = "12")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entradas del diario encontradas",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionDiaryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No se encontró el plan o el diario", content = @Content)
            }
    )
    @GetMapping("/{cuit}/nutrition_plans/{id}/diary")
    public ResponseEntity<List<NutritionDiaryDTO>> viewNutriPlanDiary(@PathVariable(value = "cuit") String clientCuit,
                                                                      @PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(nutritionDiaryService.readByNutritionPlan(clientCuit, id), HttpStatus.OK);
    }

    // --------------------------------------------------------
    @Operation(
            summary = "Ver el diario nutricional del plan activo",
            description = "Retorna todas las entradas del diario asociadas al plan nutricional actualmente activo del cliente.",
            parameters = {
                    @Parameter(name = "cuit", description = "CUIT del cliente", example = "20123456789")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entradas del diario del plan activo encontradas",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionDiaryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente o plan activo no encontrado", content = @Content)
            }
    )
    @GetMapping("/{cuit}/nutrition_plans/active/diary")
    public ResponseEntity<List<NutritionDiaryDTO>> viewActivePlanDiary(@PathVariable(value = "cuit") String clientCuit) {
        return new ResponseEntity<>(nutritionDiaryService.readByClientActivePlan(clientCuit), HttpStatus.OK);
    }

}
