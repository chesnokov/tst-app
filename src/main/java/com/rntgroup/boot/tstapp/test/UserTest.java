package com.rntgroup.boot.tstapp.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter @Setter
public class UserTest {
	private final  String id;
	private final String name;
	private List<Question> questions;

	public UserTest(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public UserTest(String id, String name, List<Question> questions) {
		this.id = id;
		this.name = name;
		this.questions = questions;
	}

	public UserTest(String name, List<Question> questions) {
		this.id = "";
		this.name = name;
		this.questions = questions;
	}
}
