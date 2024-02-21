package com.itau.bankapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public ServiceUnavailableException(String message) {
		super(message);
	}
}