package dev.ludovicamorales.gearguardians.controllers;

import dev.ludovicamorales.gearguardians.models.Client;
import dev.ludovicamorales.gearguardians.models.DocType;
import dev.ludovicamorales.gearguardians.services.ClientService;
import dev.ludovicamorales.gearguardians.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/client")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(){
        return new ResponseEntity<List<Client>>(clientService.allClients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable String id){
        return new ResponseEntity<Client>(clientService.clientById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addClient(@Valid @RequestBody Client client){

        Client newClient = client;

        Map<String, Object> response = new HashMap<>();

        try {
            newClient.setName(client.getName());
            newClient.setLastname(client.getLastname());
            newClient.setEmail(client.getEmail());
            newClient.setDocType(client.getDocType());
            newClient.setDocNum(client.getDocNum());
            newClient.setPhoneNum(client.getPhoneNum());

            newClient = clientService.saveClient(newClient);
            /*notificationService.sendSMS("+57"+client.getPhoneNum(), "Bienvenido(a) " + client.getName() + " a Gear Guardians.");*/

        }catch(DataAccessException e) {
            response.put("Message", "An error occurred during the query.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "The client has been successfully created.");
        response.put("Client", newClient);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@Valid @RequestBody Client client, @PathVariable String id) {

        Client foundClient = clientService.clientById(id);

        Map<String, Object> response = new HashMap<>();

        if(foundClient == null) {
            response.put("Message", "The entered id ".concat(id.toString().concat(" doesn't exist in the database.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        Client updateClient = null;

        try {
            updateClient.setName(client.getName());
            updateClient.setLastname(client.getLastname());
            updateClient.setEmail(client.getEmail());
            updateClient.setDocType(client.getDocType());
            updateClient.setDocNum(client.getDocNum());
            updateClient.setPhoneNum(client.getPhoneNum());

            updateClient = clientService.saveClient(updateClient);

        } catch(DataAccessException e) {
            response.put("Message", "An error occurred during the query.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "The client has been successfully upgraded.");
        response.put("Client", updateClient);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable String id) {

        Client foundClient = clientService.clientById(id);

        Map<String, Object> response = new HashMap<>();

        if(foundClient == null) {
            response.put("Message", "The entered id ".concat(id.toString().concat(" doesn't exist in the database.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clientService.delete(id);
        }catch(DataAccessException e) {
            response.put("Message", "An error occurred during the query.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "The client has been successfully deleted.");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @GetMapping("/document-type")
    public List<String> getDocTypes() {
        return Arrays.asList(DocType.values()).stream().map(Enum::name).collect(Collectors.toList());
    }
}
