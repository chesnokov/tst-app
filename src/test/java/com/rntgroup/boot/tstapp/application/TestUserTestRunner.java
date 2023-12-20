package com.rntgroup.boot.tstapp.application;

import com.rntgroup.boot.tstapp.repository.CompositeUserTestRepository;
import com.rntgroup.boot.tstapp.service.StreamInputOutputService;
import com.rntgroup.boot.tstapp.service.TimestampSupplier;
import com.rntgroup.boot.tstapp.service.UserTestResultService;
import com.rntgroup.boot.tstapp.test.UserTestResult;
import com.rntgroup.boot.tstapp.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes={UserTestRunner.class})
class TestUserTestRunner {
	@MockBean
	private TimestampSupplier timestampSupplier;
	@MockBean
	private CompositeUserTestRepository compositeUserTestRepository;
	@MockBean
	private StreamInputOutputService ioService;
	@Autowired
	private UserTestRunner runner;
	@MockBean
	private UserTestResultService userTestResultService;
	@MockBean
	private ConversionService conversionService;

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
				.thenReturn(Collections.singletonList(UserTestUtil.getUserTest()));
		when(ioService.getUserInput(anyString()))
				.thenReturn("1")
				.thenReturn("q");
		when(ioService.getUserInputAsInt(anyString()))
				.thenReturn(Optional.of(2))
				.thenReturn(Optional.of(1));
		when(timestampSupplier.get())
				.thenReturn(new Timestamp(0));

		runner.run();

		verify(userTestResultService, times(1))
				.processResult(new UserTestResult("testName",
						2, 2, timestampSupplier.get()));
	}

	@Test
	void shouldGetOneOfTwoResult() {
		when(compositeUserTestRepository.findAll())
				.thenReturn(Collections.singletonList(UserTestUtil.getUserTest()));
		when(ioService.getUserInput(anyString()))
				.thenReturn("1")
				.thenReturn("q");
		when(ioService.getUserInputAsInt(anyString()))
				.thenReturn(Optional.of(1))
				.thenReturn(Optional.of(1));
		when(timestampSupplier.get())
				.thenReturn(new Timestamp(0));

		runner.run();

		verify(userTestResultService, times(1))
				.processResult(new UserTestResult("testName",
						1, 2, timestampSupplier.get()));
	}

}
