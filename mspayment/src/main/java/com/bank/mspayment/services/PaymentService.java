package com.bank.mspayment.services;

import com.bank.mspayment.config.CalculateServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bank.mspayment.dto.calculatedto.CalculateRequestDTO;
import com.bank.mspayment.dto.calculatedto.CalculateResponseDTO;
import com.bank.mspayment.dto.paymentdto.PaymentRequestDTO;
import com.bank.mspayment.dto.paymentdto.PaymentResponseDTO;
import com.bank.mspayment.entity.Payment;
import com.bank.mspayment.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final CalculateServicesClient calculateServiceClient;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(CalculateServicesClient calculateServiceClient, PaymentRepository paymentRepository) {
        this.calculateServiceClient = calculateServiceClient;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequest) {
        Payment payment = new Payment(
                UUID.randomUUID(),
                paymentRequest.getCustomerId(),
                paymentRequest.getCategoryId(),
                paymentRequest.getTotal(),
                LocalDateTime.now()
        );
        paymentRepository.save(payment);

        CalculateRequestDTO calculateRequest = new CalculateRequestDTO(paymentRequest.getCategoryId(), paymentRequest.getTotal());
        ResponseEntity<CalculateResponseDTO> responseEntity = calculateServiceClient.calculatePoints(calculateRequest);
        CalculateResponseDTO calculateResponse = responseEntity.getBody();

        if (calculateResponse == null) {
            throw new RuntimeException("Failed to calculate points");
        }

        int totalPoints = calculateResponse.getTotal();


        return new PaymentResponseDTO(paymentRequest.getCustomerId(), totalPoints);
    }

    public Optional<Payment> findById(UUID id){
        return paymentRepository.findById(String.valueOf(id));
    }
}
