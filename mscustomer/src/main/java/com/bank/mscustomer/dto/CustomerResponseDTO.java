package com.bank.mscustomer.dto;

import com.bank.mscustomer.config.json.CustomLocalDateDeserializer;
import com.bank.mscustomer.config.json.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerResponseDTO{

    private Long id;
    private String cpf;
    private String name;
    private String gender;

    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate birthdate;
    private String email;
    private int points;
    private String urlPhoto;

}