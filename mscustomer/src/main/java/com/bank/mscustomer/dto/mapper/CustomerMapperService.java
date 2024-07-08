package com.bank.mscustomer.dto.mapper;

import com.bank.mscustomer.dto.CustomerRequestDTO;
import com.bank.mscustomer.dto.CustomerResponseDTO;
import com.bank.mscustomer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerMapperService {

    private final ModelMapper modelMapper;

    public Customer toEntity(CustomerRequestDTO customerRequestDTO){
        return modelMapper.map(customerRequestDTO, Customer.class);
    }

    public CustomerResponseDTO toResposenDTO(Customer customer){
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }
}
