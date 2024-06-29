package com.bank.mspayment.dto.mapper;

import com.bank.mspayment.dto.paymentdto.PaymentResponseDTO;
import com.bank.mspayment.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMapperService {

    private final ModelMapper modelMapper;

    public Payment toEntity(PaymentResponseDTO paymentResponseDTO){
        return modelMapper.map(paymentResponseDTO, Payment.class);
    }

    public PaymentResponseDTO toResponseDTO(Object payment){
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }
}
