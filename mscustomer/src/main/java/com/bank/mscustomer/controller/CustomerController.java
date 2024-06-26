package com.bank.mscustomer.controller;

import com.bank.mscustomer.dto.CustomerRequestDTO;
import com.bank.mscustomer.dto.CustomerResponseDTO;
import com.bank.mscustomer.dto.mapper.CustomerMapperService;
import com.bank.mscustomer.entity.Customer;
import com.bank.mscustomer.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapperService customerMapperService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody CustomerRequestDTO requestDTO){
        Customer customer = customerMapperService.toEntity(requestDTO);
        Customer saved = customerService.save(customer);
        CustomerResponseDTO responseDTO = customerMapperService.toResposenDTO(saved);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findAll(){
        List<Customer> customers = customerService.findAll();
        List<CustomerResponseDTO> responseDTOS = customers.stream()
                .map(customerMapperService::toResposenDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Long id){
        return customerService.findById(id)
                .map(customer -> ResponseEntity.ok(customerMapperService.toResposenDTO(customer)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @RequestBody CustomerRequestDTO customerRequestDTO){
        Customer customer = customerMapperService.toEntity(customerRequestDTO);
        return ResponseEntity.ok(customerMapperService.toResposenDTO(customerService.update(id, customer)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if (customerService.findById(id).isPresent()){
            customerService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
