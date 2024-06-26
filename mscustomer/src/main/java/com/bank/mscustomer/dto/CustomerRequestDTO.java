package com.bank.mscustomer.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CustomerRequestDTO {

    private String cpf;
    private String name;
    private String gender;
    private LocalDate birthdate;
    private String email;
    private String urlPhoto;
}
