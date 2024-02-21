package com.itau.bankapi.services;

import com.itau.bankapi.dtos.TransferRequestDTO;

public interface BacenService {
	void notifyBacen(TransferRequestDTO transferRequest);
}
