package dev.ludovicamorales.gearguardians.repositories;

import dev.ludovicamorales.gearguardians.models.Campus;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampusRepository extends MongoRepository<Campus, String> {
}
