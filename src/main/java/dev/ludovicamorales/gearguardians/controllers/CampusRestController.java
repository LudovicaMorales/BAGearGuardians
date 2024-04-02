package dev.ludovicamorales.gearguardians.controllers;

import dev.ludovicamorales.gearguardians.models.Campus;
import dev.ludovicamorales.gearguardians.services.CampusService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/campus")
public class CampusRestController {

    @Autowired
    private CampusService campusService;

    @GetMapping
    public ResponseEntity<List<Campus>> getAllCampus(){
        return new ResponseEntity<>(campusService.allCampus(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCampusById(@PathVariable String id) {

        Campus campus;

        Map<String, Object> response = new HashMap<>();

        try {
            campus = campusService.campusById(id);
        } catch(DataAccessException e) {
            response.put("Message", "An error occurred during the query.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(campus == null) {
            response.put("Message", "The entered id ".concat(id.toString().concat(" doesn't exist in the database.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(campus, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addCampus(@RequestBody Campus campus){

        Campus newCampus;

        Map<String, Object> response = new HashMap<>();

        try {
            newCampus = campusService.saveCampus(campus);
        }catch(DataAccessException e) {
            response.put("Message", "An error occurred during the query.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "The campus has been successfully created.");
        response.put("Vehicle", newCampus);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
