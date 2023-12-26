package com.rntgroup.boot.tstapp.shell;

import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class AnswerQuestionAvailability implements Supplier<Availability> {

	private ShellExecutionContext shellExecutionContext;

	public AnswerQuestionAvailability(ShellExecutionContext shellExecutionContext) {
		this.shellExecutionContext = shellExecutionContext;
	}

	@Override
	public Availability get() {
		UserTest currentTest = shellExecutionContext.getRunningTest();
		return currentTest != null ? Availability.available() :
				Availability.unavailable("no running exercise");
	}
}
