package com.bank.mscalculate.dto.ruledto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RuleRequestDTO {

    @NotBlank
    private String category;
    private int parity;
}
