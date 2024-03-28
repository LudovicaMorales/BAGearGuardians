package dev.ludovicamorales.gearguardians.services;

import dev.ludovicamorales.gearguardians.models.Vehicle;
import dev.ludovicamorales.gearguardians.repositories.VehicleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> allVehicles(){
        return (List<Vehicle>) vehicleRepository.findAll();
    }

    public Vehicle vehicleById(ObjectId id){
        return vehicleRepository.findById(id).orElse(null);
    }

    public Vehicle saveVehicle(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }

    public void delete(ObjectId id){
        vehicleRepository.deleteById(id);
    }

}
