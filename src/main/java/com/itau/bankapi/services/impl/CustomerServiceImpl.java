package com.itau.bankapi.services.impl;

import com.itau.bankapi.dtos.Customer;
import com.itau.bankapi.exceptions.ServiceUnavailableException;
import com.itau.bankapi.services.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Override
	public Optional<Customer> findCustomer(String accountNumber) {
		try {
			Customer customer = RestClient.create()
					.get()
					.uri("https://bank-api.free.beeceptor.com/customers")
					.retrieve()
					.body(Customer.class);
			return Optional.ofNullable(customer);
		} catch (Exception e) {
			throw new ServiceUnavailableException("Service unavailable");
		}
	}
}
