package com.rntgroup.boot;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.boot.tstapp.repository.CsvUserTestReader;
import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestCsvUserTestReader {
	private CsvUserTestReader userTestReader;

	@BeforeEach
	public void setUp() {
		userTestReader = new CsvUserTestReader();
	}

	@Test
	public void shouldMakeEmptyUserTest() throws IOException, CsvValidationException {

		UserTest expectedUserTest = new UserTest("testName", new ArrayList<>());

		try(CSVReader reader = new CSVReader(new InputStreamReader(TestCsvUserTestReader.class.getResourceAsStream("/test2.csv")))) {
			UserTest userTest = userTestReader.makeUserTest("testName", reader);
			assertThat(userTest).usingRecursiveComparison().isEqualTo(expectedUserTest);
		}
	}

	@Test
	public void shouldMakeUserTest() throws IOException, CsvValidationException {

		UserTest expectedUserTest = getUserTest();

		try(CSVReader reader = new CSVReader(new InputStreamReader(TestCsvUserTestReader.class.getResourceAsStream("/test1.csv")))) {
			UserTest userTest = userTestReader.makeUserTest("testName", reader);
			assertThat(userTest).usingRecursiveComparison().isEqualTo(expectedUserTest);
		}
	}

	private UserTest getUserTest() {
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
