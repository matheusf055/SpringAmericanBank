package com.bank.mscalculate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Data
public class CalculateRequestDTO {

    @NotNull
    private Long categoryId;

    @NotNull
    private double value;
}
