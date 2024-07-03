package com.bank.mscustomer.entity;

import com.bank.mscustomer.config.CustomLocalDateDeserializer;
import com.bank.mscustomer.config.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
@NoArgsConstructor @AllArgsConstructor @Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    @Column(unique = true)
    private String cpf;

    @NotBlank
    @Size(min = 3)
    private String name;

    @NotBlank
    @Pattern(regexp = "Masculino|Feminino")
    private String gender;

    @NotNull
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate birthdate;

    @NotBlank
    @Email
    private String email;

    private int points = 0;

    private String urlPhoto;

    public Customer(Long id, String cpf, String name, String gender, LocalDate birthdate, String email, String urlPhoto){
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.urlPhoto = urlPhoto;
    }
}
