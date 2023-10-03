package com.rntgroup.boot.tstapp.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

	private final Application application;

	public ConsoleRunner(Application application) {
		this.application = application;
	}

	@Override
	public void run(String... args) throws Exception {
		application.run();
	}
}
