package com.gym.fit_power.controller;

import java.net.URI;
import java.util.List;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.net.URISyntaxException;

import com.gym.fit_power.dto.ClientDTO;
import com.gym.fit_power.service.impl.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.gym.fit_power.constant.ClientConstants.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/clients")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ClientController {

    private final ClientServiceImpl clientService;


    @Autowired
    public ClientController(ClientServiceImpl clientService,
                            NutriPlanServiceImpl nutritionPlanService,
                            NutritionDiaryServiceImpl nutritionDiaryService) {
        this.clientService = clientService;
    }

    // <<<<<<<<<<<<<<<<<<< CLIENTS >>>>>>>>>>>>>>>>>>> //

    // Metodo para extraer el cuit de forma directa solo para pruebas
//    @GetMapping(value = "/prueba")
//    public String cuitTesting(@RequestHeader String authorization){
//        return DecodeUtil.extractCuitFromToken(authorization);
//    }

    @PostMapping("/create")
    public ResponseEntity<ClientDTO> create(
            @RequestBody ClientDTO request

    ) throws URISyntaxException {
        log.info("Creating new client: " + "{}", request);
        ClientDTO response = clientService.create(request);
        log.info("the client was created");
        return ResponseEntity.ok().headers(newHeader("CREATED")).
                location(new URI("/api/clients/" + response.getCuit())).body(response);
    }

    @GetMapping(value = "/{cuit}")
    public ResponseEntity<ClientDTO> readOne(
            @PathVariable(value = "cuit") String cuit) {

        log.info("Get client with cuit: " + "{}", cuit);
        ClientDTO response = clientService.readByCuit(cuit);
        log.info("Client was successfully found");
        return ResponseEntity.ok().headers(newHeader(FOUND)).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> readAll() {
        log.info("Get all clients");
        List<ClientDTO> response = new ArrayList<>(clientService.readAll());
        log.info("Clients were successfully found");
        return ResponseEntity.ok().headers(newHeader(FOUND)).body(response);
    }

    @PutMapping(value = "/{cuit}")
    public ResponseEntity<ClientDTO> update(@PathVariable(value = "cuit") String cuit, @RequestBody ClientDTO client) {
        log.info("Update personal data of client with cuit: " + "{}", cuit);
        ClientDTO response = clientService.update(cuit, client);
        log.info("the personal data is updated");
        return ResponseEntity.ok().headers(newHeader("UPDATED")).body(response);
    }

    @PutMapping(value = "/{cuit}/{gymCode}")
    public ResponseEntity<ClientDTO> changeGym(@PathVariable(value = "cuit") String clientCuit,
                                               @PathVariable(value = "gymCode") String gymCode) {
        log.info("Change the gym of the client " + "{}", clientCuit);
        ClientDTO response = clientService.changeGym(clientCuit, gymCode);
        log.info("The gym has changed successfully");
        return ResponseEntity.ok().headers(newHeader("GYM_CHANGED")).body(response);
    }

    @DeleteMapping(value = "/{cuit}")
    public ResponseEntity<ClientDTO> disable(@PathVariable(value = "cuit") String cuit) {
        log.info("Disabling client with cuit: " + "{}", cuit);
        ClientDTO response = clientService.disable(cuit);
        log.info("Client disabled");
        return ResponseEntity.ok().headers(newHeader("DISABLED")).body(response);
    }

    @PatchMapping(value = "/{cuit}")
    public ResponseEntity<ClientDTO> enable(@PathVariable(value = "cuit") String cuit) {
        log.info("Enabling client with cuit: " + "{}", cuit);
        ClientDTO response = clientService.enable(cuit);
        log.info("Client enabled");
        return ResponseEntity.ok().headers(newHeader("ENABLED")).body(response);
    }

    private HttpHeaders newHeader(String headerName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, SUCCESSFUL);
        return headers;
    }


}
