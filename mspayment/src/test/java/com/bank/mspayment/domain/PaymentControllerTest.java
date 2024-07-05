package com.bank.mspayment.domain;

import com.bank.mspayment.controller.PaymentController;
import com.bank.mspayment.dto.mapper.PaymentMapperService;
import com.bank.mspayment.dto.paymentdto.PaymentRequestDTO;
import com.bank.mspayment.dto.paymentdto.PaymentResponseDTO;
import com.bank.mspayment.entity.Payment;
import com.bank.mspayment.services.PaymentServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentServices paymentServices;

    @Mock
    private PaymentMapperService paymentMapperService;

    @Test
    public void processPayment_ValidRequest_ReturnsCreated() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO(1L, 2L, 100.0);
        PaymentResponseDTO responseDTO = new PaymentResponseDTO(1L, 200); // Exemplo de pontos calculados

        when(paymentServices.processPayment(any(PaymentRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<PaymentResponseDTO> responseEntity = paymentController.processPayment(requestDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo(responseDTO);
    }

    @Test
    public void findById_ExistingId_ReturnsPaymentResponse() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId, 1L, 2L, 100.0, LocalDateTime.now());
        PaymentResponseDTO responseDTO = new PaymentResponseDTO(payment.getCustomerId(), 200); // Exemplo de pontos calculados

        when(paymentServices.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentMapperService.toResponseDTO(payment)).thenReturn(responseDTO);

        ResponseEntity<PaymentResponseDTO> responseEntity = paymentController.findById(paymentId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(responseDTO);
    }

    @Test
    public void findById_NonExistingId_ReturnsNotFound() {
        UUID nonExistingId = UUID.randomUUID();

        when(paymentServices.findById(nonExistingId)).thenReturn(Optional.empty());

        ResponseEntity<PaymentResponseDTO> responseEntity = paymentController.findById(nonExistingId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
