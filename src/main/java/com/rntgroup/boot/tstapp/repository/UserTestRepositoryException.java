package com.rntgroup.boot.tstapp.repository;

public class UserTestRepositoryException extends RuntimeException {
	public UserTestRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserTestRepositoryException(String message) {
		super(message);
	}
}
