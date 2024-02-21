package com.itau.bankapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.bankapi.dtos.TransferRequestDTO;
import com.itau.bankapi.services.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@AutoConfigureMockMvc
@ContextConfiguration
class BankControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Mock
	private BankService bankService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	//	@Mock
	//	private Resilience4JCircuitBreakerFactory circuitBreakerFactory;

	@InjectMocks
	private BankController bankController;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(bankController, "bankService", bankService);
		mockMvc = MockMvcBuilders.standaloneSetup(bankController).build();
	}

	@Test
	void testGetBalances() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/balances/{accountNumber}", 123456))
				.andExpect(status().isOk());
	}

	@Test
	void testMakeTransfer() throws Exception {
		TransferRequestDTO transferRequest = new TransferRequestDTO();
		transferRequest.setSourceAccount("123456");
		transferRequest.setTargetAccount("789012");
		transferRequest.setValue(new BigDecimal(200));
		mockMvc.perform(MockMvcRequestBuilders.post("/transfers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transferRequest)))
				.andExpect(status().isOk());
	}
}
