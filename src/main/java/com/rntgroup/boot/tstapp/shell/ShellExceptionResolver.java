package com.rntgroup.boot.tstapp.shell;

import org.springframework.shell.command.CommandExceptionResolver;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.stereotype.Component;

@Component
public class ShellExceptionResolver implements CommandExceptionResolver {
	@Override
	public CommandHandlingResult resolve(Exception ex) {
		if(ex instanceof ShellException) {
			return CommandHandlingResult.of(ex.getMessage() + '\n', ((ShellException) ex).getExitCode());
		}
		throw new ShellException("unknown exception", ex, 1);
	}
}
