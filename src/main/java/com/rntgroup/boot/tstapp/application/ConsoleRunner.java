package com.rntgroup.boot.tstapp.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class ConsoleRunner implements CommandLineRunner {
	private final UserTestRunner userTestRunner;

	public ConsoleRunner(UserTestRunner userTestRunner) {
		this.userTestRunner = userTestRunner;
	}

	@Override
	public void run(String... args) throws Exception {
		userTestRunner.run();
	}
}
