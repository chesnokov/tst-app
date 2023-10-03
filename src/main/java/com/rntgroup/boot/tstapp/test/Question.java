package com.rntgroup.boot.tstapp.test;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Question {
	private final String text;
	private final List<Answer> answers;

	public Question(String text, List<Answer> answers) {
		this.text = text;
		this.answers = answers;
	}
}
