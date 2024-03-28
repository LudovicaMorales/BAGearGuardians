package dev.ludovicamorales.gearguardians.controllers;

import dev.ludovicamorales.gearguardians.models.*;
import dev.ludovicamorales.gearguardians.models.Record;
import dev.ludovicamorales.gearguardians.services.CampusService;
import dev.ludovicamorales.gearguardians.services.ClientService;
import dev.ludovicamorales.gearguardians.services.RecordService;
import dev.ludovicamorales.gearguardians.services.VehicleService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/record")
public class RecordRestController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private CampusService campusService;

    @GetMapping
    public ResponseEntity<List<Record>> getAllRecords(){
        return new ResponseEntity<>(recordService.allRecords(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable ObjectId id){
        return new ResponseEntity<>(recordService.recordById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addRecord(@RequestBody Record record){

        Client client = record.getClient();

        Vehicle vehicle = record.getVehicle();

        Campus campus = record.getCampus();

        Map<String, Object> response = new HashMap<>();

        try {
            record.setCampus(campus);
            record.setDescription(record.getDescription());
            record.setClient(client);
            record.setVehicle(vehicle);
            record.setParts(record.getParts());
            record.setStartTime(record.getStartTime());
            record.setEndTime(record.getEndTime());

            recordService.saveRecord(record);
        }catch(DataAccessException e) {
            response.put("Message", "An error occurred during the query.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "The record has been successfully created.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecord(@RequestBody Record record, @PathVariable ObjectId id) {

        Record foundRecord = recordService.recordById(id);

        Client client = record.getClient();

        Map<String, Object> response = new HashMap<>();

        if(foundRecord == null) {
            response.put("Message", "The entered id ".concat(id.toString().concat(" doesn't exist in the database.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Record updateRecord = null;

        try {
            updateRecord.setCampus(record.getCampus());
            updateRecord.setDescription(record.getDescription());
            updateRecord.setClient(client);
            updateRecord.setParts(record.getParts());
            updateRecord.setStartTime(record.getStartTime());
            updateRecord.setEndTime(record.getEndTime());

            recordService.saveRecord(updateRecord);

        } catch(DataAccessException e) {
            response.put("Message", "An error occurred during the query.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "The record has been successfully upgraded.");
        response.put("Record", updateRecord);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable ObjectId id) {

        Record foundRecord = recordService.recordById(id);

        Map<String, Object> response = new HashMap<>();

        if(foundRecord == null) {
            response.put("Message", "The entered id ".concat(id.toString().concat(" doesn't exist in the database.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            recordService.deleteRecord(id);
        }catch(DataAccessException e) {
            response.put("Message", "An error occurred during the query.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "The record has been successfully deleted.");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/service-type")
    public List<String> getServiceType() {
        return Arrays.stream(ServiceType.values()).map(Enum::name).collect(Collectors.toList());
    }

    @GetMapping("/parts")
    public List<String> getParts() {
        return Arrays.stream(Parts.values()).map(Enum::name).collect(Collectors.toList());
    }
}
