package com.rntgroup.boot.tstapp.shell;

import com.rntgroup.boot.tstapp.service.UserTestResultService;
import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTestResult;
import org.springframework.core.convert.ConversionService;
import org.springframework.shell.command.CommandContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

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
		processCurrentQuestionAnswers(commandContext.getRawArgs());

		if(shellExecutionContext.hasNextQuestion()) {
			return getNextQuestionToShow();
		} else {
			return getUserTestResultAndFinishTest();
		}
	}

	private String getUserTestResultAndFinishTest() {
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

	private void processCurrentQuestionAnswers(String [] args) {
		if(args.length == 0) {
			throw new ShellException("index or indexes of correct answers are required", 2);
		}
		long correctAnswers = shellExecutionContext.getCurrentQuestion().getCorrectAnswersCount();
		List<Answer> answers = shellExecutionContext.getCurrentQuestionAnswers();
		long correctCount = getCorrectAnswersCountInUserInput(args, answers);
		long wrongCount = getWrongAnswersCountInUserInput(args, answers);
		if(correctAnswers == correctCount && wrongCount == 0) {
			shellExecutionContext.incrementCorrectAnswersCount();
		}
	}

	private long getCorrectAnswersCountInUserInput(String[] args, List<Answer> answers) {
		return getAnswerCorrectnessStream(args, answers)
				.filter(a -> a).count();
	}

	private long getWrongAnswersCountInUserInput(String[] args, List<Answer> answers) {
		return getAnswerCorrectnessStream(args, answers)
				.filter(a -> !a).count();
	}

	private Stream<Boolean> getAnswerCorrectnessStream(String[] args, List<Answer> answers) {

		Stream<String> answersStream  = Arrays.stream(args)
				.flatMap( s -> Arrays.stream(s.split(",")));

		Stream<Integer> answerIndexesStream = answersStream
				.map(s -> {
					try {
						return Integer.parseInt(s);
					} catch (NumberFormatException e) {
						throw new ShellException(String.format(
								"'%s' not a number, index of answer required", s), 2);
					}
				});

		return  answerIndexesStream
				.map( idx -> {
						if (idx < 1 || idx > answers.size()) {
							throw new ShellException(String.format(
									"no answer with specified index '%d'", idx),2);
						}
						return answers.get(idx - 1).isCorrect();
				});

	}
}
