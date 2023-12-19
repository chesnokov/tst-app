package com.rntgroup.boot.tstapp.shell;

import com.rntgroup.boot.tstapp.service.UserTestResultService;
import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTestResult;
import org.springframework.core.convert.ConversionService;
import org.springframework.shell.command.CommandContext;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Component
public class AnswerQuestionCommand implements Function<CommandContext, String> {

	private final ShellExecutionContext shellExecutionContext;
	private final ConversionService conversionService;
	private final UserTestResultService userTestResultService;

	public AnswerQuestionCommand(ShellExecutionContext shellExecutionContext,
								 ConversionService conversionService,
								 UserTestResultService userTestResultService) {
		this.shellExecutionContext = shellExecutionContext;
		this.conversionService = conversionService;
		this.userTestResultService = userTestResultService;
	}

	@Override
	public String apply(CommandContext commandContext) {
		processCurrentQuestionAnswer(commandContext.getRawArgs());

		if(shellExecutionContext.hasNextQuestion()) {
			return getNextQuestionToShow();
		} else {
			return processUserTestResultAndFinishTest();
		}
	}

	private String processUserTestResultAndFinishTest() {
		UserTestResult userTestResult = shellExecutionContext.getUserTestResult();
		userTestResultService.processResult(userTestResult);
		String result = conversionService.convert(userTestResult, String.class);
		shellExecutionContext.setCurrentTest(null);
		return result;
	}

	private String getNextQuestionToShow() {
		Question question = shellExecutionContext.nextQuestion();
		return conversionService.convert(question, String.class);
	}

	private long getCorrectAnswersCountInUserInput(String[] args, List<Answer> answers) {
		try {
			return Arrays.stream(args)
					.flatMap( s -> Arrays.stream(s.split(",")))
					.map(s -> {
						int idx = Integer.parseInt(s);
						if (idx < 1 || idx > answers.size()) {
							throw new ShellException(MessageFormat.format(
									"no answer with specified index {0}", idx),2);
						}
						return answers.get(idx - 1).isCorrect();
					})
					.filter(a -> a).count();
		} catch (NumberFormatException e) {
			throw new ShellException("one or more answer indexes should be specified",2);
		}
	}

	private void processCurrentQuestionAnswer(String [] args) {
		List<Answer> answers = shellExecutionContext.getCurrentQuestionAnswers();
		long correctAnswers = shellExecutionContext.getCurrentQuestion().getCorrectAnswersCount();
		long userCorrectAnswers = getCorrectAnswersCountInUserInput(args, answers);
		if(correctAnswers == userCorrectAnswers) {
			shellExecutionContext.incrementCorrectAnswersCount();
		}
	}
}
