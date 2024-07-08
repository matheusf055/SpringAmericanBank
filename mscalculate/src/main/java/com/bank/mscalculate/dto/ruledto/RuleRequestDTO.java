package com.bank.mscalculate.dto.ruledto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RuleRequestDTO {

    @NotBlank
    private String category;

    @NotNull
    private int parity;
}
