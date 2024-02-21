package com.itau.bankapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public UnprocessableEntityException(String message) {
		super(message);
	}
}