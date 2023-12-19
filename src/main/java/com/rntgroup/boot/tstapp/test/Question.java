package com.rntgroup.boot.tstapp.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter @Setter
public class Question {
	private final String id;
	private final String testId;
	private final String text;
	private List<Answer> answers;

	public Question(String id, String testId, String text) {
		this.id = id;
		this.testId = testId;
		this.text = text;
	}

	public Question(String id, String testId, String text, List<Answer> answers) {
		this.id = id;
		this.testId = testId;
		this.text = text;
		this.answers = answers;
	}

	public Question(String text, List<Answer> answers) {
		this.id = "";
		this.testId = "";
		this.text = text;
		this.answers = answers;
	}

	public long getCorrectAnswersCount() {
		return answers.stream().filter(Answer::isCorrect).count();
	}
}
