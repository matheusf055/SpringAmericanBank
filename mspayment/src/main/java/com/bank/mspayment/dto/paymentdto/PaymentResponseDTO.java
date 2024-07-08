package com.bank.mspayment.dto.paymentdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class PaymentResponseDTO {

    private Long customerId;
    private Double total;
}
