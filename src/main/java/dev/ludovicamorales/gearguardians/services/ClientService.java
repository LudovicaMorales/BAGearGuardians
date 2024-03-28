package dev.ludovicamorales.gearguardians.services;

import dev.ludovicamorales.gearguardians.models.Client;
import dev.ludovicamorales.gearguardians.repositories.ClientRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> allClients(){
        List<Client> ejemplos = clientRepository.findAll();
        // Imprimir en la consola los valores devueltos por el método findAll()
        for (Client ejemplo : ejemplos) {
            System.out.println(ejemplo);
        }
        return ejemplos;
    }

    public Client clientById(ObjectId id){
        return clientRepository.findById(id).orElse(null);
    }

    public Client saveClient(Client client){
        return clientRepository.save(client);
    }

    public void delete(ObjectId id){
        clientRepository.deleteById(id);
    }

}
