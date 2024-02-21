package com.itau.bankapi.services.impl;

import com.itau.bankapi.dtos.TransferRequestDTO;
import com.itau.bankapi.exceptions.ServiceUnavailableException;
import com.itau.bankapi.services.BacenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class BacenServiceImpl implements BacenService {
	@Override
	public void notifyBacen(TransferRequestDTO transferRequest) {
		ResponseEntity<Void> response = RestClient.create()
				.post()
				.uri("https://bacen.free.beeceptor.com/transfer")
				.contentType(MediaType.APPLICATION_JSON)
				.body(transferRequest)
				.retrieve()
				.toBodilessEntity();
		if (HttpStatus.OK != response.getStatusCode()) {
			throw new ServiceUnavailableException("Service unavailable");
		}
	}
}
