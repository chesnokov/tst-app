package com.rntgroup.boot.tstapp.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter @Setter
public class Question {
	private final String id;
	private final String text;
	private List<Answer> answers;

	public Question(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public Question(String id, String text, List<Answer> answers) {
		this.id = id;
		this.text = text;
		this.answers = answers;
	}

	public Question(String text, List<Answer> answers) {
		this.id = "";
		this.text = text;
		this.answers = answers;
	}
}
