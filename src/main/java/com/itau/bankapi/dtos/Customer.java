package com.itau.bankapi.dtos;

public class Customer {
	private final String name;
	private final String accountNumber;

	public Customer(String name, String accountNumber) {
		this.name = name;
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

}
