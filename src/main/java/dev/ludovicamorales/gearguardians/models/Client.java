package dev.ludovicamorales.gearguardians.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    private String id;

    @NotNull(message = "The 'name' field is required")
    @NotBlank(message = "The 'name' field is required")
    private String name;

    @NotNull(message = "The 'lastname' field is required")
    @NotBlank(message = "The 'lastname' field is required")
    private String lastname;

    @NotNull(message = "The 'email' field is required")
    @NotBlank(message = "The 'email' field is required")
    @Email
    private String email;

    @NotNull(message = "The 'docType' field is required")
    private DocType docType;

    @NotNull(message = "The 'docNum' field is required")
    @NotBlank(message = "The 'docNum' field is required")
    @Length(min = 10, max = 10, message = "The 'docNum' field must be 10 characters long")
    private String docNum;

    @NotNull(message = "The 'phoneNum' field is required")
    @NotBlank(message = "The 'phoneNum' field is required")
    @Length(min = 10, max = 10, message = "The 'phoneNum' field must be 10 characters long")
    private String phoneNum;

}
