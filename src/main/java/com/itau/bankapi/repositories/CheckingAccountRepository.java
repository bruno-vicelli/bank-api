package com.itau.bankapi.repositories;

import com.itau.bankapi.entities.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {

	CheckingAccount findByAccountNumber(String accountNumber);
}
