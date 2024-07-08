package com.bank.mscustomer.domain;

import static com.bank.mscustomer.common.CustomerConstants.CUSTOMER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bank.mscustomer.controller.CustomerController;
import com.bank.mscustomer.dto.mapper.CustomerMapperService;
import com.bank.mscustomer.entity.Customer;
import com.bank.mscustomer.services.AwsS3Services;
import com.bank.mscustomer.services.CustomerServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate birthDate = LocalDate.parse("01/01/2000", formatter);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerServices customerServices;

    @MockBean
    private CustomerMapperService customerMapperService;

    @MockBean
    private AwsS3Services awsS3Services;

    @Test
    public void createCustomer_WithValidData_ReturnsCreated() throws Exception {
        when(customerServices.save(CUSTOMER)).thenReturn(CUSTOMER);

        mockMvc.perform(post("/v1/customers").content(objectMapper.writeValueAsString(CUSTOMER)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void createCustomer_WithInvalidData_ReturnsBadRequest() throws Exception {
        Customer emptyCustomer = new Customer();
        Customer invalidCustomer = new Customer(1L, "", "", "", birthDate, "", "");

        mockMvc.perform(post("/v1/customers")
                        .content(objectMapper.writeValueAsString(emptyCustomer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/v1/customers")
                        .content(objectMapper.writeValueAsString(invalidCustomer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCustomer_WithExistingCpf_ReturnsConflict() throws Exception{
        when(customerServices.save(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/v1/customers")
                        .content(objectMapper.writeValueAsString(CUSTOMER))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getCustomer_ByExistingId_ReturnsCustomer() throws Exception{
        when(customerServices.findById(1L)).thenReturn(Optional.of(CUSTOMER));

        mockMvc.perform(get("/v1/customers/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCustomer_ByUnexistingId_ReturnsCustomer() throws Exception{
        mockMvc.perform(get("/v1/customers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllCustomers_ReturnsListOfCustomers() throws Exception {
        List<Customer> customers = Arrays.asList(
                CUSTOMER,
                new Customer(2L, "987.654.321-00", "Jane Doe", "Feminino", birthDate, "janedoe@example.com", "")
        );

        when(customerServices.findAll()).thenReturn(customers);

        mockMvc.perform(get("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(customers.size()));
    }

    @Test
    public void updateCustomer_WithValidData_ReturnsOk() throws Exception {
        Customer updatedCustomer = new Customer(
                1L, "234.342.451-19", "Maria Updated", "Feminino", CUSTOMER.getBirthdate(), "mariaupdated@email.com", "photobase");

        when(customerServices.update(any(Long.class), any(Customer.class))).thenReturn(updatedCustomer);

        mockMvc.perform(put("/v1/customers/1")
                        .content(objectMapper.writeValueAsString(updatedCustomer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void removeCustomer_WithUnexistingId_ReturnsNotFound() throws Exception {
        final Long customerId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(customerServices).delete(customerId);

        mockMvc.perform(delete("/v1/customers/" + customerId))
                .andExpect(status().isNotFound());
    }
}

