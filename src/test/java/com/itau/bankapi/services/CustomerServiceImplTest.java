package com.itau.bankapi.services;

import com.itau.bankapi.dtos.Customer;
import com.itau.bankapi.exceptions.ServiceUnavailableException;
import com.itau.bankapi.exceptions.UnprocessableEntityException;
import com.itau.bankapi.services.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImplTest {

	@InjectMocks
	private CustomerServiceImpl customerService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void findCustomerSuccess() {
		Optional<Customer> customer = customerService.findCustomer("123456");
		Assertions.assertTrue(customer.isPresent());
		Assertions.assertEquals("123456", customer.get().getAccountNumber());
	}
}