package dev.ludovicamorales.gearguardians.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "records")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @Id
    private String id;

    @DocumentReference(lazy = true)
    private Client client;

    @DocumentReference(lazy = true)
    private Vehicle vehicle;

    private ServiceType serviceType;

    private Campus campus;

    private Status status;

    private String description;

    private List<Parts> parts;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
