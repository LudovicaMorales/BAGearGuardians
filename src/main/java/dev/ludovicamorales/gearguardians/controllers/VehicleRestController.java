package dev.ludovicamorales.gearguardians.controllers;

import dev.ludovicamorales.gearguardians.models.Vehicle;
import dev.ludovicamorales.gearguardians.services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/vehicle")
public class VehicleRestController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles(){
        return new ResponseEntity<List<Vehicle>>(vehicleService.allVehicles(), HttpStatus.OK);
    }

    @GetMapping("/{plate}")
    public ResponseEntity<?> getVehicleByPlate(@PathVariable String plate) {

        Vehicle vehicle = null;

        Map<String, Object> response = new HashMap<>();

        try {
            vehicle = vehicleService.vehicleByPlate(plate);
        } catch(DataAccessException e) {
            response.put("message", "An error occurred during the query.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(vehicle == null) {
            response.put("message", "The entered id ".concat(plate.toString().concat(" doesn't exist in the database.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addVehicle(@Valid @RequestBody Vehicle vehicle){

        Vehicle newVehicle = null;

        Map<String, Object> response = new HashMap<>();

        try {
            newVehicle = vehicleService.saveVehicle(vehicle);
        }catch(DataAccessException e) {
            response.put("message", "An error occurred during the query.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The vehicle has been successfully created.");
        response.put("vehicle", newVehicle);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
