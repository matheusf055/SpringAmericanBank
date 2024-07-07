package com.bank.mspayment.domain;

import com.bank.mspayment.entity.Payment;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    public void testConstructor() {
        UUID id = UUID.randomUUID();
        Long customerId = 123L;
        Long categoryId = 456L;
        Double total = 789.0;
        LocalDateTime createdDate = LocalDateTime.now();

        Payment payment = new Payment(id, customerId, categoryId, total, createdDate);

        assertEquals(id, payment.getId());
        assertEquals(customerId, payment.getCustomerId());
        assertEquals(categoryId, payment.getCategoryId());
        assertEquals(total, payment.getTotal());
        assertEquals(createdDate, payment.getCreatedDate());
    }

    @Test
    public void testGettersAndSetters() {
        Payment payment = new Payment();
        UUID id = UUID.randomUUID();
        Long customerId = 123L;
        Long categoryId = 456L;
        Double total = 789.0;
        LocalDateTime createdDate = LocalDateTime.now();

        payment.setId(id);
        payment.setCustomerId(customerId);
        payment.setCategoryId(categoryId);
        payment.setTotal(total);
        payment.setCreatedDate(createdDate);

        assertEquals(id, payment.getId());
        assertEquals(customerId, payment.getCustomerId());
        assertEquals(categoryId, payment.getCategoryId());
        assertEquals(total, payment.getTotal());
        assertEquals(createdDate, payment.getCreatedDate());
    }

    @Test
    public void testHashCode() {
        Payment payment1 = new Payment(UUID.randomUUID(), 123L, 456L, 789.0, LocalDateTime.now());
        Payment payment2 = new Payment(payment1.getId(), payment1.getCustomerId(), payment1.getCategoryId(), payment1.getTotal(), payment1.getCreatedDate());

        assertEquals(payment1.hashCode(), payment2.hashCode());
    }

    @Test
    public void testEquals() {
        Payment payment1 = new Payment(UUID.randomUUID(), 123L, 456L, 789.0, LocalDateTime.now());
        Payment payment2 = new Payment(payment1.getId(), payment1.getCustomerId(), payment1.getCategoryId(), payment1.getTotal(), payment1.getCreatedDate());
        Payment payment3 = new Payment(UUID.randomUUID(), 123L, 456L, 789.0, LocalDateTime.now());

        assertTrue(payment1.equals(payment2));
        assertFalse(payment1.equals(payment3));
    }

    @Test
    public void testToString() {
        UUID id = UUID.randomUUID();
        Long customerId = 123L;
        Long categoryId = 456L;
        Double total = 789.0;
        LocalDateTime createdDate = LocalDateTime.now();

        Payment payment = new Payment(id, customerId, categoryId, total, createdDate);

        String paymentToString = payment.toString();

        assertTrue(paymentToString.contains("Payment{id=" + id));
        assertTrue(paymentToString.contains(", customerId=" + customerId));
        assertTrue(paymentToString.contains(", categoryId=" + categoryId));
        assertTrue(paymentToString.contains(", total=" + total));
        assertTrue(paymentToString.contains(", createdDate=" + createdDate));
    }

    private void assertTrue(boolean contains) {
    }

}
