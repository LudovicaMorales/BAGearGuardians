package dev.ludovicamorales.gearguardians.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "The 'chassis' field is required")
    @NotBlank(message = "The 'chassis' field is required")
    private String chassis;

    @NotNull(message = "The 'plate' field is required")
    @NotBlank(message = "The 'plate' field is required")
    private String plate;

    @NotNull(message = "The 'brand' field is required")
    @NotBlank(message = "The 'brand' field is required")
    private String brand;

    @NotNull(message = "The 'model' field is required")
    @NotBlank(message = "The 'model' field is required")
    private String model;

    @NotNull(message = "The 'year' field is required")
    @Min(value = 1900, message = "The field 'year' must be between 1900 and 2025")
    @Max(value = 2025, message = "The field 'year' must be between 1900 and 2025")
    private Integer year;

    @NotNull(message = "The 'mileage' field is required")
    private Integer mileage;
}
