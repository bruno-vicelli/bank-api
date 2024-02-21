package com.itau.bankapi.services;

import com.itau.bankapi.entities.CheckingAccount;
import com.itau.bankapi.dtos.Customer;
import com.itau.bankapi.dtos.TransferRequestDTO;
import com.itau.bankapi.exceptions.UnprocessableEntityException;
import com.itau.bankapi.repositories.CheckingAccountRepository;
import com.itau.bankapi.services.impl.BankServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BankServiceTest {
	@Mock
	private CheckingAccountRepository checkingAccountRepository;
	@Mock
	private BacenService bacenService;
	@Mock
	private CustomerService customerService;

	@InjectMocks
	private BankServiceImpl bankService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetBalancesSuccess() {
		CheckingAccount checkingAccount = new CheckingAccount();
		checkingAccount.setBalance(new BigDecimal(1000));
		checkingAccount.setAccountNumber("123456");
		checkingAccount.setActive(true);
		checkingAccount.setCustomer(createCustomer());
		when(checkingAccountRepository.findByAccountNumber("123456")).thenReturn(checkingAccount);
		when(customerService.findCustomer("123456")).thenReturn(Optional.of(createCustomer()));

		BigDecimal balance = bankService.getBalance("123456");
		Assertions.assertEquals(checkingAccount.getBalance(), balance);
		verify(checkingAccountRepository, times(1)).findByAccountNumber("123456");
		verify(customerService, times(1)).findCustomer("123456");
	}

	@Test
	void testGetBalancesWithCheckingAccountNull() {
		when(checkingAccountRepository.findByAccountNumber("123456")).thenReturn(null);
		when(customerService.findCustomer("123456")).thenReturn(Optional.of(createCustomer()));

		Exception thrown = Assertions.assertThrows(UnprocessableEntityException.class, () -> {
			BigDecimal balance = bankService.getBalance("123456");
			Assertions.assertEquals(BigDecimal.ZERO, balance);
		});
		Assertions.assertEquals("Unable to recover balance", thrown.getMessage());
		verify(checkingAccountRepository, times(1)).findByAccountNumber("123456");
	}

	@Test
	void testGetBalancesWithCheckingAccountInactive() {
		CheckingAccount checkingAccount = new CheckingAccount();
		checkingAccount.setBalance(new BigDecimal(1000));
		checkingAccount.setAccountNumber("123456");
		checkingAccount.setActive(false);
		checkingAccount.setCustomer(createCustomer());
		when(checkingAccountRepository.findByAccountNumber("123456")).thenReturn(checkingAccount);
		when(customerService.findCustomer("123456")).thenReturn(Optional.of(createCustomer()));

		Exception thrown = Assertions.assertThrows(UnprocessableEntityException.class, () -> {
			BigDecimal balance = bankService.getBalance("123456");
			Assertions.assertEquals(BigDecimal.ZERO, balance);
		});
		Assertions.assertEquals("Unable to recover balance", thrown.getMessage());
		verify(checkingAccountRepository, times(1)).findByAccountNumber("123456");
	}

	@Test
	void testGetBalancesWithNotFoundCustomer() {
		CheckingAccount checkingAccount = new CheckingAccount();
		checkingAccount.setBalance(new BigDecimal(1000));
		checkingAccount.setAccountNumber("123456");
		checkingAccount.setActive(true);
		checkingAccount.setCustomer(createCustomer());
		when(checkingAccountRepository.findByAccountNumber("123456")).thenReturn(checkingAccount);
		when(customerService.findCustomer("123456")).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(UnprocessableEntityException.class, () -> {
			BigDecimal balance = bankService.getBalance("123456");
			Assertions.assertEquals(BigDecimal.ZERO, balance);
		});
		Assertions.assertEquals("Unable to recover balance", thrown.getMessage());
		verify(checkingAccountRepository, times(1)).findByAccountNumber("123456");
		verify(customerService, times(1)).findCustomer("123456");
	}

	private Customer createCustomer() {
		return new Customer("David Sanborn", "123456");
	}

	@Test
	void testMakeTransferSuccess() throws Exception {
		TransferRequestDTO transferRequest = new TransferRequestDTO();
		transferRequest.setSourceAccount("123456");
		transferRequest.setTargetAccount("789012");
		transferRequest.setValue(new BigDecimal(200));

		CheckingAccount checkingSourceAccount = new CheckingAccount();
		checkingSourceAccount.setBalance(new BigDecimal(1000));
		checkingSourceAccount.setAccountNumber("123456");
		checkingSourceAccount.setActive(true);
		checkingSourceAccount.setCustomer(createCustomer());

		CheckingAccount checkingTargetAccount = new CheckingAccount();
		checkingTargetAccount.setBalance(new BigDecimal(1000));
		checkingTargetAccount.setAccountNumber("789012");
		checkingTargetAccount.setActive(true);
		Customer customerTarget = new Customer("Michael Brecker", "789012");
		checkingTargetAccount.setCustomer(customerTarget);

		when(checkingAccountRepository.findByAccountNumber("123456")).thenReturn(checkingSourceAccount);
		when(customerService.findCustomer("123456")).thenReturn(Optional.of(createCustomer()));
		when(checkingAccountRepository.findByAccountNumber("789012")).thenReturn(checkingTargetAccount);
		when(customerService.findCustomer("789012")).thenReturn(Optional.of(customerTarget));

		bankService.makeTransfer(transferRequest);
		verify(customerService, times(1)).findCustomer("123456");
		verify(checkingAccountRepository, times(1)).findByAccountNumber("123456");
		verify(customerService, times(1)).findCustomer("789012");
		verify(checkingAccountRepository, times(1)).findByAccountNumber("789012");
		checkingSourceAccount.setBalance(new BigDecimal(800));
		verify(checkingAccountRepository, times(1)).saveAndFlush(checkingSourceAccount);
		checkingTargetAccount.setBalance(new BigDecimal(1200));
		verify(checkingAccountRepository, times(1)).saveAndFlush(checkingTargetAccount);
		verify(bacenService, times(1)).notifyBacen(transferRequest);
	}
}
