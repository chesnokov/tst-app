package com.rntgroup.boot.tstapp.test;

import lombok.Getter;

@Getter
public class UserTestResult {
	private final String testName;
	private final int correctAnswers;
	private final int answersCount;

	public UserTestResult(String testName, int correctAnswers, int answersCount) {
		this.testName = testName;
		this.correctAnswers = correctAnswers;
		this.answersCount = answersCount;
	}
}
