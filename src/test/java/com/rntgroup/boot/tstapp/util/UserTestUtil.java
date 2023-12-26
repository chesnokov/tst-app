package com.rntgroup.boot.tstapp.util;

import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;

import java.util.ArrayList;
import java.util.List;

public class UserTestUtil {
	public static UserTest getUserTest() {
		List<Question> expectedQuestions = new ArrayList<>();
		List<Answer> answers1 = new ArrayList<>();
		answers1.add(new Answer(null,"Answer1", false));
		answers1.add(new Answer(null,"Answer2", true));
		expectedQuestions.add(new Question("Question1", answers1));
		List<Answer> answers2 = new ArrayList<>();
		answers2.add(new Answer(null,"Answer1", true));
		answers2.add(new Answer(null,"Answer2", false));
		answers2.add(new Answer(null,"Answer3", false));
		expectedQuestions.add(new Question("Question2", answers2));
		return new UserTest("Csv Internal test 1", expectedQuestions);
	}

	public static List<UserTest> getUserTests() {
		List<UserTest> userTests =  new ArrayList<>();
		List<Question> questions = new ArrayList<>();
				List<Answer> e1q1 = new ArrayList<>();
				e1q1.add(new Answer(null,"Answer 1", true));
				e1q1.add(new Answer(null,"Answer 2", false));
			questions.add(new Question(null, null,"Question 1", e1q1));
				List<Answer> e1q2 = new ArrayList<>();
				e1q2.add(new Answer(null,"Answer 1", false));
				e1q2.add(new Answer(null,"Answer 2", false));
				e1q2.add(new Answer(null,"Answer 3", true));
			questions.add(new Question(null,null,"Question 2", e1q2));
				List<Answer> e1q3 = new ArrayList<>();
				e1q3.add(new Answer(null,"Answer 1", true));
				e1q3.add(new Answer(null,"Answer 2", false));
				e1q3.add(new Answer(null,"Answer 3", false));
				e1q3.add(new Answer(null,"Answer 4", false));
			questions.add(new Question(null,null,"Question 3", e1q3));

		userTests.add(new UserTest(null, "Exercise 1", questions));

		List<Question> questions2 = new ArrayList<>();
			List<Answer> e2q1 = new ArrayList<>();
			e2q1.add(new Answer(null,"Answer 1", true));
			e2q1.add(new Answer(null,"Answer 2", false));
		questions2.add(new Question(null, null,"Question 1", e2q1));
			List<Answer> e2q2 = new ArrayList<>();
			e2q2.add(new Answer(null,"Answer 1", false));
			e2q2.add(new Answer(null,"Answer 2", true));
		questions2.add(new Question(null, null, "Question 2", e2q2));

		userTests.add(new UserTest(null, "Exercise 2", questions2));

		return userTests;
	}
}
