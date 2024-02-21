package com.itau.bankapi.controllers;

import com.itau.bankapi.dtos.TransferRequestDTO;
import com.itau.bankapi.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class BankController {

	@Autowired
	private BankService bankService;

	@GetMapping("/balances/{accountNumber}")
	public ResponseEntity<BigDecimal> getBalances(@PathVariable String accountNumber) {
		return new ResponseEntity<>(bankService.getBalance(accountNumber), HttpStatus.OK);
	}

	@PostMapping("/transfers")
	public void makeTransfer(@RequestBody TransferRequestDTO transferRequest) {
		bankService.makeTransfer(transferRequest);
	}

}
