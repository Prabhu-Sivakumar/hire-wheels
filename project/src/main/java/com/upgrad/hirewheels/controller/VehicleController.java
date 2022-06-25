package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.services.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hirewheels/v1")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/vehicles", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<VehicleDTO>> getVehicles() {
        List<Vehicle> vehicleList = this.vehicleService.getAllVehicles();
        List<VehicleDTO> vehicleDTOList = Optional.ofNullable(vehicleList).map(vehicles -> vehicles.stream()
                .map(vehicle -> this.modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
        return ResponseEntity.ok(vehicleDTOList);
    }
}
