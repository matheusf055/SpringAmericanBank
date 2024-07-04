package com.bank.mspayment.config.openfeing;

import com.bank.mspayment.dto.calculatedto.CalculateRequestDTO;
import com.bank.mspayment.dto.calculatedto.CalculateResponseDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mscalculate", path = "/v1")
public interface CalculateServicesClient {

    @PostMapping("/calculate")
    ResponseEntity<CalculateResponseDTO> calculatePoints(@Valid @RequestBody CalculateRequestDTO requestDTO);
}
