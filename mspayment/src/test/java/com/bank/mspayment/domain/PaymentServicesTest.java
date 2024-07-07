package com.bank.mspayment.domain;

import com.bank.mspayment.config.openfeing.CalculateServicesClient;
import com.bank.mspayment.dto.calculatedto.CalculateRequestDTO;
import com.bank.mspayment.dto.calculatedto.CalculateResponseDTO;
import com.bank.mspayment.dto.paymentdto.PaymentRequestDTO;
import com.bank.mspayment.dto.paymentdto.PaymentResponseDTO;
import com.bank.mspayment.entity.Payment;
import com.bank.mspayment.repository.PaymentRepository;
import com.bank.mspayment.services.PaymentServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServicesTest {

    @InjectMocks
    private PaymentServices paymentServices;

    @Mock
    private CalculateServicesClient calculateServiceClient;

    @Mock
    private PaymentRepository paymentRepository;

    @BeforeEach
    public void setUp() {}

    @Test
    public void processPayment_ValidPaymentRequest_ReturnsPaymentResponse() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO(1L, 2L, 100.0);
        CalculateRequestDTO calculateRequestDTO = new CalculateRequestDTO(requestDTO.getCategoryId(), requestDTO.getTotal());
        CalculateResponseDTO calculateResponseDTO = new CalculateResponseDTO(200);

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment payment = invocation.getArgument(0);
            payment.setId(UUID.randomUUID());
            return payment;
        });

        when(calculateServiceClient.calculatePoints(any(CalculateRequestDTO.class))).thenReturn(ResponseEntity.ok(calculateResponseDTO));

        PaymentResponseDTO responseDTO = paymentServices.processPayment(requestDTO);

        assertThat(responseDTO.getCustomerId()).isEqualTo(requestDTO.getCustomerId());
        assertThat(responseDTO.getPoints()).isEqualTo(calculateResponseDTO.getTotal());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(calculateServiceClient, times(1)).calculatePoints(calculateRequestDTO);
    }

    @Test
    public void findById_ExistingId_ReturnsPayment() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId, 1L, 2L, 100.0, LocalDateTime.now());

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        Optional<Payment> foundPayment = paymentServices.findById(paymentId);

        assertThat(foundPayment).isPresent();
        assertThat(foundPayment.get().getId()).isEqualTo(paymentId);
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    public void findById_NonExistingId_ReturnsEmptyOptional() {
        UUID nonExistingId = UUID.randomUUID();

        when(paymentRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Optional<Payment> foundPayment = paymentServices.findById(nonExistingId);

        assertThat(foundPayment).isEmpty();
        verify(paymentRepository, times(1)).findById(nonExistingId);
    }
}
