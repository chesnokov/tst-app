package com.rntgroup.boot.tstapp.shell;

import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.shell.command.CommandContext;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;

@Component
public class RunTestCommand implements Function<CommandContext, String> {

	private final ShellExecutionContext shellExecutionContext;
	private final ConversionService conversionService;

	public RunTestCommand(ShellExecutionContext shellExecutionContext,
						  ConversionService conversionService) {
		this.shellExecutionContext = shellExecutionContext;
		this.conversionService = conversionService;
	}

	@Override
	public String apply(CommandContext commandContext) {
		String[] args = commandContext.getRawArgs();
		if(args.length < 1)
			throw new ShellException("run-text <index> required", 2);

		int testIndex = getTestIndex(args[0]);
		UserTest userTest = getUserTest(testIndex);
		shellExecutionContext.setCurrentTest(userTest);
		return getQuestionToShow();
	}

	private int getTestIndex(String index) {
		try {
			return Integer.parseInt(index);
		} catch(NumberFormatException e) {
			throw new ShellException(MessageFormat.format(
				"Numeric index of test required '{0}' is not a number", index), e, 2);
		}
	}

	private UserTest getUserTest(int index) {
		List<UserTest> userTests = shellExecutionContext.getUserTests();
		if(index < 1 || index > userTests.size()) {
			throw new ShellException(MessageFormat.format("No user test with index {0}",
					index), 2);
		}
		return shellExecutionContext.getUserTests().get(index - 1);
	}

	private String getQuestionToShow() {
		Question currentQuestion = shellExecutionContext.getCurrentQuestion();
		return conversionService.convert(currentQuestion, String.class);
	}
}
