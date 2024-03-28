package dev.ludovicamorales.gearguardians.controllers;

import dev.ludovicamorales.gearguardians.models.Vehicle;
import dev.ludovicamorales.gearguardians.services.VehicleService;
import org.bson.types.ObjectId;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable ObjectId id) {

        Vehicle vehicle = null;

        Map<String, Object> response = new HashMap<>();

        try {
            vehicle = vehicleService.vehicleById(id);
        } catch(DataAccessException e) {
            response.put("Message", "An error occurred during the query.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(vehicle == null) {
            response.put("Message", "The entered id ".concat(id.toString().concat(" doesn't exist in the database.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle){

        Vehicle newVehicle = null;

        Map<String, Object> response = new HashMap<>();

        try {
            newVehicle = vehicleService.saveVehicle(vehicle);
        }catch(DataAccessException e) {
            response.put("Message", "An error occurred during the query.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "The vehicle has been successfully created.");
        response.put("Vehicle", newVehicle);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
