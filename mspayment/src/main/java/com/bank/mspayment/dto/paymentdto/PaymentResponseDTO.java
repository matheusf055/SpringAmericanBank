package com.bank.mspayment.dto.paymentdto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {

    private Long customerId;
    private int points;
}
