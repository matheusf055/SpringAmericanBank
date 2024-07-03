package com.bank.mscustomer.common;

import com.bank.mscustomer.entity.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerConstants {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate birthDate = LocalDate.parse("01/01/2000", formatter);

    public static final Customer CUSTOMER = new Customer
            (1L,"234.342.451-19", "Maria", "Feminino", birthDate, "maria@email.com", "photobase");

    public static final Customer INVALID_CUSTOMER = new Customer
            (1L, "", "", " ", birthDate, "", "");
}
