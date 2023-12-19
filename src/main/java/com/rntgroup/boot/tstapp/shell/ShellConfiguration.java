package com.rntgroup.boot.tstapp.shell;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.command.CommandRegistration;

@Configuration
public class ShellConfiguration {

	@Bean
	CommandRegistration showTests(ShowTestsCommand showTestsCommand,
			ShellExceptionResolver shellExceptionResolver) {

		return CommandRegistration.builder()
				.command("show-tests")
				.withAlias()
					.command("s")
				.and()
				.description("show available tests")
				.withTarget()
				.function(showTestsCommand)
				.and()
				.build();
	}

	@Bean
	CommandRegistration runTest(RunTestCommand runTestCommand,
			RunTestAvailability runTestAvailability,
			ShellExceptionResolver shellExceptionResolver) {

		return CommandRegistration.builder()
				.command("run-test")
				.withAlias()
					.command("r")
				.and()
				.description("<index> run test specified by index in the list")
				.availability(runTestAvailability)
				.withTarget()
					.function(runTestCommand)
				.and()
				.build();
	}

	@Bean
	CommandRegistration answerQuestion(AnswerQuestionCommand answerQuestionCommand,
			   AnswerQuestionAvailability answerQuestionAvailability,
			   ShellExceptionResolver shellExceptionResolver) {

		return CommandRegistration.builder()
				.command("answer")
				.withAlias()
					.command("a")
				.and()
				.description("<index>,[index]... give answer with index")
				.availability(answerQuestionAvailability)
				.withTarget()
					.function(answerQuestionCommand)
				.and()
				.build();
	}
}
