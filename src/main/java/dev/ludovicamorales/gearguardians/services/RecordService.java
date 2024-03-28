package dev.ludovicamorales.gearguardians.services;

import dev.ludovicamorales.gearguardians.models.Campus;
import dev.ludovicamorales.gearguardians.models.Record;
import dev.ludovicamorales.gearguardians.repositories.RecordRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public List<Record> allRecords(){
        return (List<Record>) recordRepository.findAll();
    }

    public Record recordById(ObjectId id){
        return recordRepository.findById(id).orElse(null);
    }

    public Record saveRecord(Record record){
        return recordRepository.save(record);
    }

    public void deleteRecord(ObjectId id){
        recordRepository.deleteById(id);
    }
}
