package com.rntgroup.boot.tstapp.shell;

import org.springframework.shell.Availability;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class RunTestAvailability implements Supplier<Availability> {

	private ShellExecutionContext shellExecutionContext;

	public RunTestAvailability(ShellExecutionContext shellExecutionContext) {
		this.shellExecutionContext = shellExecutionContext;
	}

	@Override
	public Availability get() {
		var userTests =  shellExecutionContext.getUserTests();
		return userTests != null && userTests.size() > 0 ? Availability.available() :
				Availability.unavailable("you need to execute show-tests first");
	}
}
