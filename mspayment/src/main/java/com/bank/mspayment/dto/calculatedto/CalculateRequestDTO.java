package com.bank.mspayment.dto.calculatedto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class CalculateRequestDTO {

    @NotNull
    private Long categoryId;

    @NotNull
    private double value;
}
