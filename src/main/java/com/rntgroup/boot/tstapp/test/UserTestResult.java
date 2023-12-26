package com.rntgroup.boot.tstapp.test;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter @Setter
@EqualsAndHashCode
@ToString
public class UserTestResult {
	private final String testName;
	private final int correctAnswersCount;
	private final int questionsCount;
	private final Timestamp timestamp;
	private long id;

	public UserTestResult(String testName, int correctAnswersCount, int questionsCount, Timestamp timestamp) {
		this.testName = testName;
		this.correctAnswersCount = correctAnswersCount;
		this.questionsCount = questionsCount;
		this.timestamp = timestamp;
	}

	public UserTestResult(long id, String testName, int correctAnswersCount,
						  int questionsCount, Timestamp timestamp) {
		this.id = id;
		this.testName = testName;
		this.correctAnswersCount = correctAnswersCount;
		this.questionsCount = questionsCount;
		this.timestamp = timestamp;
	}
}
