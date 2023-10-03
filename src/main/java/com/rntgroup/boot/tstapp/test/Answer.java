package com.rntgroup.boot.tstapp.test;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Answer {
	private final String text;
	private final boolean isCorrect;

	public Answer(String text, boolean isCorrect) {
		this.text = text;
		this.isCorrect = isCorrect;
	}
}
