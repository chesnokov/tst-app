package com.rntgroup.boot.tstapp.test.sql;

import com.rntgroup.boot.tstapp.repository.QuestionRepository;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;

import java.util.List;

public class LazySqlUserTest extends UserTest {

	private final QuestionRepository questionRepository;

	public LazySqlUserTest(QuestionRepository questionRepository, String id, String name) {
		super(id, name);
		this.questionRepository = questionRepository;
	}

	@Override
	public List<Question> getQuestions() {
		var questions = super.getQuestions();
		if(questions == null) {
			questions = questionRepository.findByUserTestId(this);
			setQuestions(questions);
		}
		return questions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserTest)) return false;
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
