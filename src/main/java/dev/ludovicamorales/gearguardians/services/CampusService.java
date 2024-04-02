package dev.ludovicamorales.gearguardians.services;

import dev.ludovicamorales.gearguardians.models.Campus;
import dev.ludovicamorales.gearguardians.repositories.CampusRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusService {

    @Autowired
    private CampusRepository campusRepository;

    public List<Campus> allCampus(){
        return campusRepository.findAll();
    }

    public Campus campusById(String id){
        return campusRepository.findById(id).orElse(null);
    }

    public Campus saveCampus(Campus campus){
        return campusRepository.save(campus);
    }

    public void deleteCampus(String id){
        campusRepository.deleteById(id);
    }
}
