package dev.ludovicamorales.gearguardians.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {



    @Id

    private String id;

    private String chassis;

    private String plate;

    private String brand;

    private String model;

    private Integer year;

    private Integer mileage;
}
