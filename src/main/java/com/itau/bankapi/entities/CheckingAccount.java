package com.itau.bankapi.entities;

import com.itau.bankapi.dtos.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "T_CHECKING_ACCOUNT")
public class CheckingAccount implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String accountNumber;
	@Transient
	private Customer customer;
	@Column
	private BigDecimal balance;
	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean active = false;
	@Column
	private final BigDecimal dailyLimit = BigDecimal.valueOf(1000);

	public Long getId() {
		return id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public BigDecimal getDailyLimit() {
		return dailyLimit;
	}
}

