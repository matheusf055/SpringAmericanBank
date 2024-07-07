package com.bank.mscustomer.domain;

import static com.bank.mscustomer.common.CustomerConstants.CUSTOMER;
import static com.bank.mscustomer.common.CustomerConstants.INVALID_CUSTOMER;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.bank.mscustomer.entity.Customer;
import com.bank.mscustomer.repository.CustomerRepository;
import com.bank.mscustomer.services.CustomerServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate birthDate = LocalDate.parse("01/01/2000", formatter);

    @InjectMocks
    private CustomerServices customerServices;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void createCustomer_WithValidData_ReturnsCustomer(){
        when(customerRepository.save(CUSTOMER)).thenReturn(CUSTOMER);

        Customer sut = customerServices.save(CUSTOMER);

        assertThat(sut).isEqualTo(CUSTOMER);
    }

    @Test
    public void createCustomer_WithInvalidData_ThorwsException(){
        when(customerRepository.save(INVALID_CUSTOMER)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> customerServices.save(INVALID_CUSTOMER)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getCustomer_ByExistingId_ReturnsCustomer(){
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(CUSTOMER));

        Optional<Customer> sut = customerServices.findById(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(CUSTOMER);
    }

    @Test
    public void getCustomer_ByUnexistingId_ReturnsEmpty(){
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Customer> sut = customerServices.findById(1L);

        assertThat(sut).isEmpty();
    }

    @Test
    public void findAllCustomers_ReturnsCustomerList(){
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(CUSTOMER));

        List<Customer> sut = customerServices.findAll();

        assertThat(sut).isNotEmpty();
        assertThat(sut).contains(CUSTOMER);
    }

    @Test
    public void update_ExistingCustomer_UpdatesCustomer() {
        Customer updatedCustomer = new Customer(1L,"234.342.451-20", "João", "Masculino", birthDate, "joao@email.com", "photobase");
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

        Customer result = customerServices.update(1L, updatedCustomer);

        assertEquals(updatedCustomer, result);
    }

    @Test
    public void update_NonExistingCustomer_ThrowsRuntimeException() {
        Customer updatedCustomer = new Customer(1L,"234.342.451-19", "Maria", "Feminino", birthDate, "maria@email.com", "photobase");
        when(customerRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerServices.update(1L, updatedCustomer);
        });

        assertEquals("Provider for jakarta.ws.rs.ext.RuntimeDelegate cannot be found", exception.getCause().getMessage());
    }

    @Test
    public void removeCustomer_WithExistingId_doesNotThrowAnyException(){
        assertThatCode(() -> customerServices.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removeCustomer_WithUnexistingId_doesNotThrowAnyException(){
        doThrow(new RuntimeException()).when(customerRepository).deleteById(99L);

        assertThatThrownBy(() -> customerServices.delete(99L)).isInstanceOf(RuntimeException.class);
    }
}
