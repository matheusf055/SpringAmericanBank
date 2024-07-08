package com.bank.mspayment.services;

import com.bank.mspayment.config.openfeing.CalculateServicesClient;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServices {

    private final CalculateServicesClient calculateServiceClient;
    private final PaymentRepository paymentRepository;

    public Optional<Payment> findById(UUID id){
        return paymentRepository.findById(id);
    }

    public List<Payment> findByCustomerId(Long customerId) {
        return paymentRepository.findByCustomerId(customerId);
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

        return new PaymentResponseDTO(paymentRequest.getCustomerId(), (double) totalPoints);
    }
}
