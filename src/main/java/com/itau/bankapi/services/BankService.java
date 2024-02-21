package com.itau.bankapi.services;

import com.itau.bankapi.dtos.TransferRequestDTO;

import java.math.BigDecimal;

public interface BankService {
	BigDecimal getBalance(String accountNumber);

	void makeTransfer(TransferRequestDTO transferRequest);
}
