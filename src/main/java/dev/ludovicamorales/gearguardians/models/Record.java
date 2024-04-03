package dev.ludovicamorales.gearguardians.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Document(collection = "records")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @Id
    private String id;

    @DocumentReference(lazy = true)
    @NotNull(message = "The 'client' field is required")
    private Client client;

    @DocumentReference(lazy = true)
    @NotNull(message = "The 'vehicle' field is required")
    private Vehicle vehicle;

    @NotNull(message = "The 'serviceType' field is required")
    private ServiceType serviceType;

    @NotNull(message = "The 'campus' field is required")
    private Campus campus;

    private Status status;

    @NotNull(message = "The 'description' field is required")
    @NotBlank(message = "The 'description' field is required")
    private String description;

    @NotNull(message = "The 'parts' field is required")
    private List<Parts> parts;

    @NotNull(message = "The 'startTime' field is required")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        this.endTime = startTime.plus(2, ChronoUnit.DAYS);
    }
}
