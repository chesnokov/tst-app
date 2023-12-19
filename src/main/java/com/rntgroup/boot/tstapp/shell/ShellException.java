package com.rntgroup.boot.tstapp.shell;

import org.springframework.boot.ExitCodeGenerator;

public class ShellException extends RuntimeException implements ExitCodeGenerator {

	private final int code;

	public ShellException(String msg, int code) {
		super(msg);
		this.code = code;
	}

	public ShellException(String msg, Throwable cause, int code) {
		super(msg, cause);
		this.code = code;
	}

	@Override
	public int getExitCode() {
		return code;
	}
}
