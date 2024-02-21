package com.itau.bankapi.services;

import com.itau.bankapi.dtos.Customer;

import java.util.Optional;

public interface CustomerService {
	Optional<Customer> findCustomer(String accountNumber);
}
