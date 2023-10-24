package com.rntgroup.boot.application;

import com.rntgroup.boot.tstapp.application.CommandLineUserTestRunner;
import com.rntgroup.boot.tstapp.application.UserTestRunner;
import com.rntgroup.boot.tstapp.repository.CompositeUserTestRepository;
import com.rntgroup.boot.tstapp.service.StreamInputOutputService;
import com.rntgroup.boot.tstapp.service.UserTestResultService;
import com.rntgroup.boot.tstapp.test.UserTestResult;
import com.rntgroup.boot.util.UserTestSupplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes=com.rntgroup.boot.tstapp.UserTestApplication.class)
class TestUserTestRunner {

	@MockBean
	CommandLineUserTestRunner commandLineUserTestRunner;
	@MockBean
	CompositeUserTestRepository compositeUserTestRepository;
	@MockBean
	StreamInputOutputService ioService;
	@Autowired
	UserTestRunner runner;
	@SpyBean
	UserTestResultService userTestResultService;

	@Test
	void shouldFinishSilently() {
		when(ioService.getUserInput(anyString()))
				.thenReturn("1")
				.thenReturn("q");

		runner.run();
	}

	@Test
	void shouldPassUserTest() {
		when(compositeUserTestRepository.findAll())
				.thenReturn(Collections.singletonList(UserTestSupplier.getUserTest()));
		when(ioService.getUserInput(anyString()))
				.thenReturn("1")
				.thenReturn("q");
		when(ioService.getUserInputAsInt(anyString()))
				.thenReturn(Optional.of(2))
				.thenReturn(Optional.of(1));

		runner.run();

		verify(userTestResultService, times(1))
				.processResult(new UserTestResult("testName", 2, 2));
	}

	@Test
	void shouldGetOneOfTwoResult() {
		when(compositeUserTestRepository.findAll())
				.thenReturn(Collections.singletonList(UserTestSupplier.getUserTest()));
		when(ioService.getUserInput(anyString()))
				.thenReturn("1")
				.thenReturn("q");
		when(ioService.getUserInputAsInt(anyString()))
				.thenReturn(Optional.of(1))
				.thenReturn(Optional.of(1));

		runner.run();

		verify(userTestResultService, times(1))
				.processResult(new UserTestResult("testName", 1, 2));
	}

}
