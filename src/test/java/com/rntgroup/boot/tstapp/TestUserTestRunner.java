package com.rntgroup.boot.tstapp;

import com.rntgroup.boot.tstapp.application.UserTestRunner;
import com.rntgroup.boot.tstapp.repository.CompositeUserTestRepository;
import com.rntgroup.boot.tstapp.service.StreamInputOutputService;
import com.rntgroup.boot.tstapp.service.UserTestResultService;
import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.tstapp.test.UserTestResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
class TestUserTestRunner {

	@MockBean
	CompositeUserTestRepository compositeUserTestRepository;
	@MockBean
	StreamInputOutputService ioService;
	@Autowired
	UserTestRunner runner;
	@SpyBean
	UserTestResultService userTestResultService;

	private UserTest getUserTest() {
		List<Question> expectedQuestions = new ArrayList<>();
		List<Answer> answers1 = new ArrayList<>();
		answers1.add(new Answer("Answer1", false));
		answers1.add(new Answer("Answer2", true));
		expectedQuestions.add(new Question("Question1", answers1));
		List<Answer> answers2 = new ArrayList<>();
		answers2.add(new Answer("Answer1", true));
		answers2.add(new Answer("Answer2", false));
		answers2.add(new Answer("Answer3", false));
		expectedQuestions.add(new Question("Question2", answers2));
		return new UserTest("testName", expectedQuestions);
	}

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
				.thenReturn(Collections.singletonList(getUserTest()));
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
				.thenReturn(Collections.singletonList(getUserTest()));
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
