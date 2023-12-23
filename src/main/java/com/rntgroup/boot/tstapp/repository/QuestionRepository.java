package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;

import java.util.List;

public interface QuestionRepository {
	List<Question> findAll();
	List<Question> findByUserTestId(UserTest userTest);
}
