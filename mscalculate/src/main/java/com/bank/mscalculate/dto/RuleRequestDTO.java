package com.bank.mscalculate.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RuleRequestDTO {

    @NotBlank
    private String category;
    private int parity;
}
