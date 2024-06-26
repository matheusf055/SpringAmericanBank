package com.bank.mscustomer.entity;

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
    private String cpf;

    @NotBlank
    @Size(min = 3)
    private String name;

    @NotBlank
    @Pattern(regexp = "Masculino|Feminino")
    private String gender;

    @NotNull
    private LocalDate birthdate;

    @NotBlank
    @Email
    private String email;

    private int points = 0;

    private String urlPhoto;
}
