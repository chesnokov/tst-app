package com.rntgroup.tstapp.tst;

public class Answer {
	String text;
	boolean isCorrect;

	public Answer(String text, boolean isCorrect) {
		this.text = text;
		this.isCorrect = isCorrect;
	}

	public String getText() {
		return text;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	@Override
	public String toString() {
		return "Answer{" +
				"text='" + text + '\'' +
				", isCorrect=" + isCorrect +
				'}';
	}
}
