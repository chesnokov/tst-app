package com.rntgroup.boot.tstapp.service;

import com.rntgroup.boot.tstapp.test.UserTestResult;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class UserTestResultService {
	private final InputOutputService ioService;
	private final ConversionService conversionService;

	public UserTestResultService(InputOutputService ioService, ConversionService conversionService) {
		this.ioService = ioService;
		this.conversionService = conversionService;
	}

	public void processResult(UserTestResult result) {
		ioService.println(conversionService.convert(result, String.class));
	}
}
