package com.rntgroup.boot.tstapp.shell;

import com.rntgroup.boot.tstapp.repository.UserTestRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.shell.command.CommandContext;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ShowTestsCommand implements Function<CommandContext, String> {

	private final ShellExecutionContext shellExecutionContext;
	private final UserTestRepository compositeUserTestRepository;
	private final ConversionService conversionService;

	public ShowTestsCommand(ShellExecutionContext shellExecutionContext,
							UserTestRepository compositeUserTestRepository,
							ConversionService conversionService) {
		this.shellExecutionContext = shellExecutionContext;
		this.compositeUserTestRepository = compositeUserTestRepository;
		this.conversionService = conversionService;
	}

	@Override
	public String apply(CommandContext commandContext) {
		var userTests = compositeUserTestRepository.findAll();
		shellExecutionContext.setUserTests(userTests);
		return conversionService.convert(userTests, String.class);
	}
}
