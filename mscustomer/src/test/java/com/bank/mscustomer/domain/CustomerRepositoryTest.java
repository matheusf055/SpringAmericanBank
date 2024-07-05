package com.bank.mscustomer.domain;

import static com.bank.mscustomer.common.CustomerConstants.CUSTOMER;
import static org.assertj.core.api.Assertions.*;

import com.bank.mscustomer.entity.Customer;
import com.bank.mscustomer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate birthDate = LocalDate.parse("01/01/2000", formatter);

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
    public void createCustomer_WithInvalidData_ThrowException(){
        Customer emptyCustomer = new Customer();
        Customer invalidCustomer = new Customer( 1L, "" , "" , "", birthDate, "", "");

        assertThatThrownBy(() -> customerRepository.save(emptyCustomer));
        assertThatThrownBy(() -> customerRepository.save(invalidCustomer));
    }

    @Test
    public void createCustomer_WithExistingCredentials_ThrowsException(){
        Customer customer = testEntityManager.merge(CUSTOMER);
        testEntityManager.detach(customer);
        customer.setId(null);

        assertThatThrownBy(() -> customerRepository.save(customer));
    }

    @Test
    public void getCustomer_ByExistingId_ReturnsCustomer(){
        Customer customer = testEntityManager.merge(CUSTOMER);

        Optional<Customer> customerOptional = customerRepository.findById(customer.getId());

        assertThat(customerOptional).isNotEmpty();
        assertThat(customerOptional.get()).isEqualTo(customer);
    }

    @Test
    public void getCustomer_ByUnexistingId_ReturnsCustomer(){
        Optional<Customer> customerOptional = customerRepository.findById(1L);

        assertThat(customerOptional).isEmpty();
    }

    @Test
    public void remoteCustomer_WithExistingId_RemovesCustomerFromDatabase(){
        Customer customer = testEntityManager.merge(CUSTOMER);

        customerRepository.deleteById(customer.getId());

        Customer removedCustomer = testEntityManager.find(Customer.class, customer.getId());
        assertThat(removedCustomer).isNull();
    }
}
