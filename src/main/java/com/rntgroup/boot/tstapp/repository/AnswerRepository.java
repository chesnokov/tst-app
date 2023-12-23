package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;

import java.util.List;

public interface AnswerRepository {
	List<Answer> findAll();
	List<Answer> findByQuestionId(Question question);
}
