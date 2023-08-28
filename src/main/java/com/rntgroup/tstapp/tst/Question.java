package com.rntgroup.tstapp.tst;

import java.util.List;

public class Question {
	String text;
	List<Answer> answers;

	public Question(String text, List<Answer> answers) {
		this.text = text;
		this.answers = answers;
	}

	public String getText() {
		return text;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	@Override
	public String toString() {
		return "Question{" +
				"text='" + text + '\'' +
				", answers=" + answers +
				'}';
	}
}
