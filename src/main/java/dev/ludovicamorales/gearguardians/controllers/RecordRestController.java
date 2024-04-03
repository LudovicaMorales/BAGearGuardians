package dev.ludovicamorales.gearguardians.controllers;

import dev.ludovicamorales.gearguardians.models.*;
import dev.ludovicamorales.gearguardians.models.Record;
import dev.ludovicamorales.gearguardians.services.*;
import jakarta.validation.Valid;
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
    
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Record>> getAllRecords(){
        return new ResponseEntity<>(recordService.allRecords(), HttpStatus.OK);
    }

    @GetMapping("/{plate}")
    public ResponseEntity<?> getRecordByVehicle(@PathVariable String plate){

        Record record;

        Map<String, Object> response = new HashMap<>();

        Vehicle vehicle = vehicleService.vehicleByPlate(plate);

        try {
            record = recordService.recordByVehicle(vehicle);
        } catch(DataAccessException e) {
            response.put("message", "An error occurred during the query.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(vehicle == null) {
            response.put("message", "The entered plate ".concat(plate.concat(" doesn't exist in the database.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("record", record);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> addRecord(@Valid @RequestBody Record record){

        Client client = clientService.clientById(record.getClient().getId());
        Vehicle vehicle = vehicleService.vehicleById(record.getVehicle().getId());
        Campus campus = campusService.campusById(record.getCampus().getId());

        Map<String, Object> response = new HashMap<>();

        if(client == null) {
            response.put("message", "The entered client id doesn't exist in the database.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if(vehicle == null) {
            response.put("message", "The entered vehicle id doesn't exist in the database.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if(campus == null) {
            response.put("message", "The entered campus id doesn't exist in the database.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            record.setCampus(campus);
            record.setDescription(record.getDescription());
            record.setClient(client);
            record.setVehicle(vehicle);
            record.setStatus(Status.valueOf("Asignación"));
            record.setParts(record.getParts());
            record.setStartTime(record.getStartTime());

            recordService.saveRecord(record);
            
            notificationService.sendSMS("+57"+client.getPhoneNum(),
                    "¡Hola "+client.getName()+"! Tu cita para " + record.getServiceType()+" de tu " + vehicle.getBrand() + vehicle.getModel() + 
                            "está confirmada para el " + record.getStartTime() + " en " + campus.getName() + ". ¡Te esperamos! ");
            
        }catch(DataAccessException e) {
            response.put("message", "An error occurred during the query.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The record has been successfully created.");
        response.put("record", record);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecord(@Valid @RequestBody Record record, @PathVariable String id) {

        Record foundRecord = recordService.recordById(id);

        Client client = clientService.clientById(record.getClient().getId());
        Vehicle vehicle = vehicleService.vehicleById(record.getVehicle().getId());
        Campus campus = campusService.campusById(record.getCampus().getId());

        Map<String, Object> response = new HashMap<>();

        if(foundRecord == null) {
            response.put("message", "The entered id ".concat(id.concat(" doesn't exist in the database.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if(client == null) {
            response.put("message", "The entered client id doesn't exist in the database.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if(vehicle == null) {
            response.put("message", "The entered vehicle id doesn't exist in the database.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if(campus == null) {
            response.put("message", "The entered campus id doesn't exist in the database.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Record updateRecord = null;

        try {
            updateRecord.setCampus(campus);
            updateRecord.setDescription(record.getDescription());
            updateRecord.setClient(client);
            updateRecord.setVehicle(vehicle);
            updateRecord.setParts(record.getParts());
            updateRecord.setStartTime(record.getStartTime());
            updateRecord.setEndTime(record.getEndTime());
            updateRecord.setStatus(record.getStatus());

            recordService.saveRecord(updateRecord);

            switch (record.getStatus()) {
                case Autorización:
                    notificationService.sendSMS("+57"+client.getPhoneNum(),
                            "GearGuardians: El servicio de mantenimiento se encuentra en autorización. Te " +
                                    "mantendremos al " +
                                    "tanto.");
                    break;
                case Repuestos:
                    notificationService.sendSMS("+57"+client.getPhoneNum(),
                            "GearGuardians: Tu moto está en espera de repuestos. Te avisaremos cuando los recibamos.");
                    break;
                case Externo:
                    notificationService.sendSMS("+57"+client.getPhoneNum(),
                            "GearGuardians: Tu moto está recibiendo un servicio externo por parte de nuestros aliados" +
                                    ". Te informaremos sobre el progreso.");
                    break;
                case Reparación:
                    notificationService.sendSMS("+57"+client.getPhoneNum(),
                            "¡Tu moto está lista para ser recogida! Gracias por confiar en GearGuardians.");
                    break;
                case Completado:
                    notificationService.sendSMS("+57"+client.getPhoneNum(),
                            "¡Gracias por confiar en nosotros! Te informamos que tu moto ya está lista para ser " +
                                    "recogida.");
                    break;
                case Cancelado:
                    notificationService.sendSMS("+57"+client.getPhoneNum(),
                            "Lamentamos informar que tu cita para el mantenimiento de moto ha sido cancelada. Te invitamos a reprogramarla cuando te sea posible.");
                    break;
            }

        } catch(DataAccessException e) {
            response.put("message", "An error occurred during the query.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The record has been successfully upgraded.");
        response.put("record", updateRecord);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable String id) {

        Record foundRecord = recordService.recordById(id);

        Map<String, Object> response = new HashMap<>();

        if(foundRecord == null) {
            response.put("message", "The entered id ".concat(id.concat(" doesn't exist in the database.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            recordService.deleteRecord(id);
        }catch(DataAccessException e) {
            response.put("message", "An error occurred during the query.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The record has been successfully deleted.");

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
