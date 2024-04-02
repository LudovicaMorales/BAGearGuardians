package dev.ludovicamorales.gearguardians.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    private String id;

    private String name;

    private String lastname;

    private String email;

    private DocType docType;

    private String docNum;

    private String phoneNum;

    private LocalDateTime createAt;
}
