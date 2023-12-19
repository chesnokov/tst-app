package com.rntgroup.boot.tstapp.application;

import com.rntgroup.boot.tstapp.annotation.AspectJBenchmark;
import com.rntgroup.boot.tstapp.repository.UserTestRepository;
import com.rntgroup.boot.tstapp.repository.UserTestRepositoryException;
import com.rntgroup.boot.tstapp.service.InputOutputService;
import com.rntgroup.boot.tstapp.service.UserTestResultService;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.tstapp.test.UserTestResult;
import org.springframework.core.convert.ConversionService;

import java.text.MessageFormat;
import java.util.List;

@Deprecated
public class UserTestRunner {
	private final UserTestRepository repository;
	private final InputOutputService ioService;
	private final ConversionService conversionService;
	private final UserTestResultService userTestResultService;

	public UserTestRunner(UserTestRepository compositeUserTestRepository,
						  InputOutputService ioService,
						  ConversionService conversionService,
						  UserTestResultService userTestResultService) {
		this.repository = compositeUserTestRepository;
		this.ioService = ioService;
		this.conversionService = conversionService;
		this.userTestResultService = userTestResultService;
	}

	@AspectJBenchmark
	public void run() {
		try {
			List<UserTest> tests = repository.findAll();
			listAndRunUserTests(tests);
		} catch (UserTestRepositoryException e) {
			ioService.println(
				MessageFormat.format("Error during user tests loading: {0}",e.getMessage()));
		}
	}

	private void listAndRunUserTests(List<UserTest> tests) {
		boolean runUserTest = true;
		while(runUserTest) {
			listUserTests(tests);
			String input = ioService.getUserInput("Choose test or q: ");
			if(input.startsWith("q")) {
				runUserTest = false;
			} else {
				int index = Integer.parseInt(input);
				if(index < 1 || index > tests.size())  {
					continue;
				}
				runTest(tests.get(index - 1));
			}
		}
	}

	private void listUserTests(List<UserTest> tests) {
		ioService.print(conversionService.convert(tests, String.class));
	}

	private void runTest(UserTest userTest)  {
		int correctCount = 0;
		for(Question question: userTest.getQuestions()) {
			boolean questionCorrect = askQuestion(question);
			if(questionCorrect) {
				correctCount++;
			}
		}
		UserTestResult userTestResult = new UserTestResult(userTest.getName(),
			correctCount, userTest.getQuestions().size());
		userTestResultService.processResult(userTestResult);
	}

	private boolean askQuestion(Question question) {
		showQuestion(question);
		int answerIndex = ioService.getUserInputAsInt("Answer: ").orElse(0);
		return isAnswerCorrect(question, answerIndex);
	}

	private boolean isAnswerCorrect(Question question, int answerIndex) {
		return answerIndex >= 1 && answerIndex <= question.getAnswers().size() &&
			question.getAnswers().get(answerIndex - 1).isCorrect();
	}

	private void showQuestion(Question question) {
		ioService.print(conversionService.convert(question, String.class));
	}
}
