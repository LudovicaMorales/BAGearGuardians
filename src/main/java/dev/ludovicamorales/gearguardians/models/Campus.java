package dev.ludovicamorales.gearguardians.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "campus")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Campus {

    @Id
    private String id;

    private String name;

    private String address;

    private String phoneNum;
}
