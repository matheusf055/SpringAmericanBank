package com.bank.mscustomer.dto;

import com.bank.mscustomer.config.json.CustomLocalDateDeserializer;
import com.bank.mscustomer.config.json.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CustomerRequestDTO {

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
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate birthdate;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    private String photo;
}
