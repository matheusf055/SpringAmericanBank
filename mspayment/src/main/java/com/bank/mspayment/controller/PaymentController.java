package com.bank.mspayment.controller;

import com.bank.mspayment.dto.mapper.PaymentMapperService;
import com.bank.mspayment.dto.paymentdto.PaymentRequestDTO;
import com.bank.mspayment.dto.paymentdto.PaymentResponseDTO;

import com.bank.mspayment.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapperService paymentMapperService;

    @PostMapping
    @Operation(summary = "Adds a payment", description = "Adds a payment", tags = {"Payment"}, responses = {
            @ApiResponse(description = "Created", responseCode = "200", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        PaymentResponseDTO paymentResponse = paymentService.processPayment(paymentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Finds a payment", description = "Finds a payment", tags = {"Payment"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PaymentResponseDTO> findById(@PathVariable UUID id) {
        return paymentService.findById(id)
                .map(payment -> ResponseEntity.ok(paymentMapperService.toResponseDTO(payment)))
                .orElse(ResponseEntity.notFound().build());
    }
}
