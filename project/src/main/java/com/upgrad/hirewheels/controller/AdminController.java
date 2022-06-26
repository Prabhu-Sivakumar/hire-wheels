package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.services.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hirewheels/v1")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/vehicles", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<VehicleDTO> AddVehicle(@RequestBody VehicleDTO vehicleDTO) {
        if (vehicleDTO != null) {
            Vehicle vehicle = this.modelMapper.map(vehicleDTO, Vehicle.class);
            try {
                Vehicle savedVehicle = this.adminService.registerVehicle(vehicle);
                VehicleDTO savedVehicleDTO = this.modelMapper.map(savedVehicle, VehicleDTO.class);
                return new ResponseEntity<>(savedVehicleDTO, HttpStatus.CREATED);
            } catch (Exception e) {
                System.out.println("Error in registering vehicle due to " + e);
            }
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PutMapping(value = "/vehicles/{id}", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<VehicleDTO> UpdateVehicle(@PathVariable("id") Integer id, @RequestBody VehicleDTO vehicleDTO) {
        Vehicle updatedVehicle = this.adminService.changeAvailability(id, vehicleDTO.getAvailabilityStatus());
        VehicleDTO updatedVehicleDTO = this.modelMapper.map(updatedVehicle, VehicleDTO.class);
        return new ResponseEntity<>(updatedVehicleDTO, HttpStatus.ACCEPTED);
    }
}
