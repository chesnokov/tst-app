package com.rntgroup.boot.tstapp.test;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Answer {
	private final String questionId;
	private final String text;
	private final boolean isCorrect;

	public Answer(String questionId, String text, boolean isCorrect) {
		this.questionId = questionId;
		this.text = text;
		this.isCorrect = isCorrect;
	}
}
