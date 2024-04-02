package dev.ludovicamorales.gearguardians.repositories;

import dev.ludovicamorales.gearguardians.models.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    Optional<Vehicle> findByPlate(String plate);
}
