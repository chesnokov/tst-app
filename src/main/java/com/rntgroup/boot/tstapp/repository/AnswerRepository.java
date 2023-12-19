package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.test.Answer;

import java.util.List;

public interface AnswerRepository {
	List<Answer> findAll();
}
