package dev.ludovicamorales.gearguardians.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "campus")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Campus {

    @Id
    private String id;

    @NotNull(message = "The 'name' field is required")
    @NotBlank(message = "The 'name' field is required")
    private String name;

    @NotNull(message = "The 'address' field is required")
    @NotBlank(message = "The 'address' field is required")
    private String address;

    @NotNull(message = "The 'phoneNum' field is required")
    @NotBlank(message = "The 'phoneNum' field is required")
    @Length(min = 10, max = 10, message = "The 'phoneNum' field must be 10 characters long")
    private String phoneNum;
}
