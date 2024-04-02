package dev.ludovicamorales.gearguardians.repositories;

import dev.ludovicamorales.gearguardians.models.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {

}
