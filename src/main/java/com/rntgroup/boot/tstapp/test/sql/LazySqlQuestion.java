package com.rntgroup.boot.tstapp.test.sql;

import com.rntgroup.boot.tstapp.repository.AnswerRepository;
import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;

import java.util.List;

public class LazySqlQuestion extends Question {
	private final AnswerRepository answerRepository;

	public LazySqlQuestion(AnswerRepository answerRepository, String id, String testId, String text) {
		super(id, testId, text);
		this.answerRepository = answerRepository;
	}

	@Override
	public List<Answer> getAnswers() {
		var answers = super.getAnswers();
		if(answers == null) {
			answers = answerRepository.findByQuestionId(this);
			setAnswers(answers);
		}
		return answers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Question)) return false;
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
