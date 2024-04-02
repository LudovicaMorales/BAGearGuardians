package dev.ludovicamorales.gearguardians.repositories;

import dev.ludovicamorales.gearguardians.models.Record;
import dev.ludovicamorales.gearguardians.models.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository extends MongoRepository<Record, String> {

    Optional<Record> findByVehicle(Vehicle vehicle);
}
