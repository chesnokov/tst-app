package com.rntgroup.boot.tstapp.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Answer {
	private Integer id;
	private Integer questionId;
	private final String text;
	private final boolean isCorrect;

	public Answer(String text, boolean isCorrect) {
		this.text = text;
		this.isCorrect = isCorrect;
	}

	public Answer(Integer questionId, String text, boolean isCorrect) {
		this.questionId = questionId;
		this.text = text;
		this.isCorrect = isCorrect;
	}
}
