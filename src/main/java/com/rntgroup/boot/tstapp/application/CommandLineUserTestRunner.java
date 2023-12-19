package com.rntgroup.boot.tstapp.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Deprecated
public class CommandLineUserTestRunner implements CommandLineRunner {
	private final UserTestRunner userTestRunner;

	public CommandLineUserTestRunner(UserTestRunner userTestRunner) {
		this.userTestRunner = userTestRunner;
	}

	@Override
	public void run(String... args) throws Exception {
		userTestRunner.run();
	}
}
