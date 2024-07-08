package com.bank.mspayment.repository;

import com.bank.mspayment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findByCustomerId(Long customerId);

    Optional<Payment> findById(UUID id);
}
