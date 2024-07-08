package com.bank.mspayment.dto.paymentdto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PaymentRequestDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private Long categoryId;

    @NotNull
    private Double total;
}