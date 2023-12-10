package com.rntgroup.boot.tstapp.service;

import com.rntgroup.boot.tstapp.repository.UserTestResultRepository;
import com.rntgroup.boot.tstapp.test.UserTestResult;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTestResultService {
	private final InputOutputService ioService;
	private final ConversionService conversionService;
	private final UserTestResultRepository userTestResultRepository;

	public UserTestResultService(InputOutputService ioService, ConversionService conversionService,
								 UserTestResultRepository userTestResultRepository) {
		this.ioService = ioService;
		this.conversionService = conversionService;
		this.userTestResultRepository = userTestResultRepository;
	}

	public void processResult(UserTestResult result) {
		userTestResultRepository.save(result);
		ioService.println(conversionService.convert(result, String.class));
	}

	public void showAllResults() {
		List<UserTestResult> results = userTestResultRepository.findAll();
		for(UserTestResult result:results) {
			ioService.println(result.toString());
		}
	}
}
