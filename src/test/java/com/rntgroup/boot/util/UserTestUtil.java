package com.rntgroup.boot.util;

import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;

import java.util.ArrayList;
import java.util.List;

public class UserTestUtil {
	public static UserTest getUserTest() {
		List<Question> expectedQuestions = new ArrayList<>();
		List<Answer> answers1 = new ArrayList<>();
		answers1.add(new Answer("Answer1", false));
		answers1.add(new Answer("Answer2", true));
		expectedQuestions.add(new Question("Question1", answers1));
		List<Answer> answers2 = new ArrayList<>();
		answers2.add(new Answer("Answer1", true));
		answers2.add(new Answer("Answer2", false));
		answers2.add(new Answer("Answer3", false));
		expectedQuestions.add(new Question("Question2", answers2));
		return new UserTest("testName", expectedQuestions);
	}
}
