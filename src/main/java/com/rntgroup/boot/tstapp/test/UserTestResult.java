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
	private final int correctAnswers;
	private final int answersCount;
	private final Timestamp timestamp;
	private long id;

	public UserTestResult(String testName, int correctAnswers, int answersCount, Timestamp timestamp) {
		this.testName = testName;
		this.correctAnswers = correctAnswers;
		this.answersCount = answersCount;
		this.timestamp = timestamp;
	}

	public UserTestResult(long id, String testName, int correctAnswers,
						  int answersCount, Timestamp timestamp) {
		this.id = id;
		this.testName = testName;
		this.correctAnswers = correctAnswers;
		this.answersCount = answersCount;
		this.timestamp = timestamp;
	}
}
