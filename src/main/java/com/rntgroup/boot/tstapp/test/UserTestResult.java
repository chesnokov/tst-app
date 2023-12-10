package com.rntgroup.boot.tstapp.test;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@EqualsAndHashCode
@ToString
public class UserTestResult {
	private final String testName;
	private final int correctAnswers;
	private final int answersCount;
	private long id;

	public UserTestResult(String testName, int correctAnswers, int answersCount) {
		this.testName = testName;
		this.correctAnswers = correctAnswers;
		this.answersCount = answersCount;
	}

	public UserTestResult(long id, String testName, int correctAnswers, int answersCount) {
		this.id = id;
		this.testName = testName;
		this.correctAnswers = correctAnswers;
		this.answersCount = answersCount;
	}
}
