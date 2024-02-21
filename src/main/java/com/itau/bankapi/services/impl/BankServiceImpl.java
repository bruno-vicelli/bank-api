package com.itau.bankapi.services.impl;

import com.itau.bankapi.entities.CheckingAccount;
import com.itau.bankapi.dtos.TransferRequestDTO;
import com.itau.bankapi.exceptions.UnprocessableEntityException;
import com.itau.bankapi.repositories.CheckingAccountRepository;
import com.itau.bankapi.services.BacenService;
import com.itau.bankapi.services.BankService;
import com.itau.bankapi.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private CheckingAccountRepository checkingAccountRepository;
	@Autowired
	private BacenService bacenService;
	@Autowired
	private CustomerService customerService;

	@Override
	public BigDecimal getBalance(String accountNumber) {
		CheckingAccount checkingAccount = checkingAccountRepository.findByAccountNumber(accountNumber);
		if (!isValidAccount(checkingAccount)) {
			throw new UnprocessableEntityException("Unable to recover balance");
		}
		return checkingAccount.getBalance();
	}

	@Override
	public void makeTransfer(TransferRequestDTO transferRequest) {
		CheckingAccount checkingSourceAccount = checkingAccountRepository.findByAccountNumber(transferRequest.getSourceAccount());
		CheckingAccount checkingTargetAccount = checkingAccountRepository.findByAccountNumber(transferRequest.getTargetAccount());
		if (isValidAccount(checkingSourceAccount) && isValidAccount(checkingTargetAccount) && isValidValues(
				transferRequest, checkingSourceAccount)) {
			checkingTargetAccount.setBalance(checkingTargetAccount.getBalance().add(transferRequest.getValue()));
			checkingSourceAccount.setBalance(checkingSourceAccount.getBalance().subtract(transferRequest.getValue()));
			checkingAccountRepository.saveAndFlush(checkingTargetAccount);
			checkingAccountRepository.saveAndFlush(checkingSourceAccount);
			bacenService.notifyBacen(transferRequest);
		}
	}

	private boolean isValidAccount(CheckingAccount checkingAccount) {
		return checkingAccount != null && checkingAccount.isActive() && customerService.findCustomer(
				checkingAccount.getAccountNumber()).isPresent();
	}

	private boolean isValidValues(TransferRequestDTO transferRequest, CheckingAccount checkingSourceAccount) {
		return transferRequest.getValue().compareTo(BigDecimal.ZERO) > 0
				&& transferRequest.getValue().compareTo(checkingSourceAccount.getBalance()) <= 0
				&& transferRequest.getValue().compareTo(checkingSourceAccount.getDailyLimit()) <= 0;
	}

}
