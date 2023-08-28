package com.rntgroup.tstapp.tst;

import java.util.List;

public class Tst {
	List<Question> questions;

	public Tst(List<Question> questions) {
		this.questions = questions;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	@Override
	public String toString() {
		return "Tst{" +
				"questions=" + questions +
				'}';
	}
}
