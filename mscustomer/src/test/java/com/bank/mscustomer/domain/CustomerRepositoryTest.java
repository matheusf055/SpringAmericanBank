package com.bank.mscustomer.domain;

import static com.bank.mscustomer.common.CustomerConstants.CUSTOMER;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.bank.mscustomer.entity.Customer;
import com.bank.mscustomer.repository.CustomerRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createCustomer_WithValidData_ReturnsCustomer(){
        Customer customer = customerRepository.save(CUSTOMER);

        Customer sut = testEntityManager.find(Customer.class, customer.getId());

        assertThat(sut).isNotNull();
    }

    @Test
    public void createCustomer_WithInvalidData_ThrowException() {
        Customer invalidCustomer = new Customer(1L, "", "", "", LocalDate.now(), "", "");

        assertThatThrownBy(() -> {
            validator.validate(invalidCustomer);
            customerRepository.save(invalidCustomer);
            testEntityManager.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void createCustomer_WithExistingCredentials_ThrowsException() {
        Customer existingCustomer = testEntityManager.find(Customer.class, 1L);

        Customer newCustomer = new Customer(null, existingCustomer.getCpf(),
                existingCustomer.getName(), existingCustomer.getGender(),
                existingCustomer.getBirthdate(), existingCustomer.getEmail(),
                existingCustomer.getUrlPhoto());

        assertThatThrownBy(() -> customerRepository.save(newCustomer))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void getCustomer_ByExistingId_ReturnsCustomer(){
        Customer customer = testEntityManager.merge(CUSTOMER);

        Optional<Customer> customerOptional = customerRepository.findById(customer.getId());

        assertThat(customerOptional).isNotEmpty();
        assertThat(customerOptional.get()).isEqualTo(customer);
    }

    @Test
    public void getCustomer_ByUnexistingId_ReturnsEmptyOptional() {
        Optional<Customer> customerOptional = customerRepository.findById(10L);

        assertFalse(customerOptional.isPresent());
    }

    @Test
    public void remoteCustomer_WithExistingId_RemovesCustomerFromDatabase(){
        Customer customer = testEntityManager.merge(CUSTOMER);

        customerRepository.deleteById(customer.getId());

        Customer removedCustomer = testEntityManager.find(Customer.class, customer.getId());
        assertThat(removedCustomer).isNull();
    }
}
