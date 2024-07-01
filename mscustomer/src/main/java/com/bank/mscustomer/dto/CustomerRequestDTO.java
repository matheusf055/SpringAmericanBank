package com.bank.mscustomer.dto;

import com.bank.mscustomer.config.CustomLocalDateDeserializer;
import com.bank.mscustomer.config.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    private String email;

    private String photo;
}
