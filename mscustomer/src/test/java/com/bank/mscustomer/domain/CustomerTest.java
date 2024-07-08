package com.bank.mscustomer.domain;

import com.bank.mscustomer.entity.Customer;
import com.bank.mscustomer.common.CustomerConstants;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void testValidCustomer() {
        Customer customer = CustomerConstants.CUSTOMER;

        assertEquals(1L, customer.getId());
        assertEquals("234.342.451-19", customer.getCpf());
        assertEquals("Maria", customer.getName());
        assertEquals("Feminino", customer.getGender());
        assertEquals(LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")), customer.getBirthdate());
        assertEquals("maria@email.com", customer.getEmail());
        assertEquals("photobase", customer.getUrlPhoto());
    }

    @Test
    public void testInvalidCustomer() {
        Customer invalidCustomer = CustomerConstants.INVALID_CUSTOMER;

        assertEquals(1L, invalidCustomer.getId());
        assertEquals("", invalidCustomer.getCpf());
        assertEquals("", invalidCustomer.getName());
        assertEquals(" ", invalidCustomer.getGender());
        assertEquals(LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")), invalidCustomer.getBirthdate());
        assertEquals("", invalidCustomer.getEmail());
        assertEquals("", invalidCustomer.getUrlPhoto());
    }

    @Test
    public void testEqualsAndHashCode() {
        Customer customer1 = CustomerConstants.CUSTOMER;
        Customer customer2 = new Customer(1L, "234.342.451-19", "Maria", "Feminino",
                LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "maria@email.com", "photobase");
        Customer customer3 = CustomerConstants.INVALID_CUSTOMER;

        assertEquals(customer1, customer2);
        assertNotEquals(customer1, customer3);
        assertEquals(customer1.hashCode(), customer2.hashCode());
        assertNotEquals(customer1.hashCode(), customer3.hashCode());
    }

    @Test
    public void testToString() {
        Customer customer = CustomerConstants.CUSTOMER;
        String expected = "Customer(id=1, cpf=234.342.451-19, name=Maria, gender=Feminino, birthdate=2000-01-01, email=maria@email.com, points=0, urlPhoto=photobase)";
        assertEquals(expected, customer.toString());
    }

    @Test
    public void testSettersAndGetters() {
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setCpf("345.678.910-11");
        customer.setName("João");
        customer.setGender("Masculino");
        customer.setBirthdate(LocalDate.parse("15/05/1990", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        customer.setEmail("joao@email.com");
        customer.setPoints(100);
        customer.setUrlPhoto("newphotobase");

        assertEquals(2L, customer.getId());
        assertEquals("345.678.910-11", customer.getCpf());
        assertEquals("João", customer.getName());
        assertEquals("Masculino", customer.getGender());
        assertEquals(LocalDate.parse("15/05/1990", DateTimeFormatter.ofPattern("dd/MM/yyyy")), customer.getBirthdate());
        assertEquals("joao@email.com", customer.getEmail());
        assertEquals(100, customer.getPoints());
        assertEquals("newphotobase", customer.getUrlPhoto());
    }

    @Test
    public void testConstructorWithAllFields() {
        LocalDate birthDate = LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Customer customer = new Customer(1L, "234.342.451-19", "Maria", "Feminino", birthDate, "maria@email.com", 10, "photobase");

        assertEquals(1L, customer.getId());
        assertEquals("234.342.451-19", customer.getCpf());
        assertEquals("Maria", customer.getName());
        assertEquals("Feminino", customer.getGender());
        assertEquals(birthDate, customer.getBirthdate());
        assertEquals("maria@email.com", customer.getEmail());
        assertEquals(10, customer.getPoints());
        assertEquals("photobase", customer.getUrlPhoto());
    }

    @Test
    public void testCanEqual() {
        Customer customer1 = CustomerConstants.CUSTOMER;
        Customer customer2 = new Customer(1L, "234.342.451-19", "Maria", "Feminino",
                LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "maria@email.com", "photobase");

        assertEquals(customer1, customer2);
    }
}

