package com.bank.mscustomer.controller;

import com.bank.mscustomer.dto.CustomerRequestDTO;
import com.bank.mscustomer.dto.CustomerResponseDTO;
import com.bank.mscustomer.dto.mapper.CustomerMapperService;
import com.bank.mscustomer.entity.Customer;
import com.bank.mscustomer.services.AwsS3Services;
import com.bank.mscustomer.services.CustomerServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Endpoints for customers")
public class CustomerController {

    private final CustomerServices customerServices;
    private final CustomerMapperService customerMapperService;
    private final AwsS3Services awsS3Services;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @PostMapping
    @Operation(summary = "Adds a customer", description = "Adds a customer", tags = {"Customers"}, responses = {
            @ApiResponse(description = "Created", responseCode = "200", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerRequestDTO requestDTO) throws IOException {
        Customer customer = customerMapperService.toEntity(requestDTO);

        if (requestDTO.getPhoto() != null && !requestDTO.getPhoto().isEmpty()) {
            String key = "photos/" + customer.getId() + ".jpg";
            awsS3Services.uploadBase64Photo(requestDTO.getPhoto(), key);
            customer.setUrlPhoto("https://s3.amazonaws.com/" + bucketName + "/" + key);
        }

        Customer saved = customerServices.save(customer);
        CustomerResponseDTO responseDTO = customerMapperService.toResposenDTO(saved);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    @Operation(summary = "Finds all customers", description = "Finds all customers", tags = {"Customers"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<List<CustomerResponseDTO>> findAll(){
        List<Customer> customers = customerServices.findAll();
        List<CustomerResponseDTO> responseDTOS = customers.stream()
                .map(customerMapperService::toResposenDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Finds a customer", description = "Finds a customer", tags = {"Customers"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Long id){
        return customerServices.findById(id)
                .map(customer -> ResponseEntity.ok(customerMapperService.toResposenDTO(customer)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a customer", description = "Updates a customer", tags = {"Customers"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200"),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @RequestBody CustomerRequestDTO customerRequestDTO){
        Customer customer = customerMapperService.toEntity(customerRequestDTO);
        return ResponseEntity.ok(customerMapperService.toResposenDTO(customerServices.update(id, customer)));
    }

    @DeleteMapping("/{id}")@Operation(summary = "Deletes a customer", description = "Deletes a customer", tags = {"Customers"}, responses = {
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if (customerServices.findById(id).isPresent()){
            customerServices.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
