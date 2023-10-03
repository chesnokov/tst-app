package com.rntgroup.boot.tstapp.test;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserTest {
	private final String name;
	private final List<Question> questions;

	public UserTest(String name, List<Question> questions) {
		this.name = name;
		this.questions = questions;
	}
}
