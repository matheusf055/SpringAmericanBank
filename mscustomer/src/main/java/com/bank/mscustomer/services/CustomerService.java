package com.bank.mscustomer.services;

import com.bank.mscustomer.entity.Customer;
import com.bank.mscustomer.repository.CustomerRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    @Transactional
    public Customer save(Customer customer){
       return repository.save(customer);
    }

    public List<Customer> findAll(){
        return repository.findAll();
    }

    public Optional<Customer> findById(Long id){
        return repository.findById(id);
    }

    public Customer update(Long id, Customer updatedCustomer){
        if (repository.existsById(id)) {
            updatedCustomer.setId(id);
            return repository.save(updatedCustomer);
        } else {
            throw new NotFoundException("Customer not found with id " + id);
        }
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
