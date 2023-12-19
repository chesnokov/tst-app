package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.test.Question;

import java.util.List;

public interface QuestionRepository {
	List<Question> findAll();
}
