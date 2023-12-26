package com.rntgroup.boot.tstapp.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter @Setter
public class Question {
	private Integer id;
	private Integer testId;
	private final String text;
	private List<Answer> answers;

	public Question(Integer id, Integer testId, String text) {
		this.id = id;
		this.testId = testId;
		this.text = text;
	}

	public Question(Integer id, Integer testId, String text, List<Answer> answers) {
		this.id = id;
		this.testId = testId;
		this.text = text;
		this.answers = answers;
	}

	public Question(String text, List<Answer> answers) {
		this.text = text;
		this.answers = answers;
	}

	public void setId(Integer id) {
		this.id = id;
		if(answers != null) {
			answers.forEach(a -> a.setQuestionId(id));
		}
	}

	public long getCorrectAnswersCount() {
		return answers.stream().filter(Answer::isCorrect).count();
	}
}
