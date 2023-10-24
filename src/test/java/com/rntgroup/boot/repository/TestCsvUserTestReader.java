package com.rntgroup.boot.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.boot.tstapp.repository.CsvUserTestReader;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.util.UserTestSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

		UserTest expectedUserTest = UserTestSupplier.getUserTest();

		try(CSVReader reader = new CSVReader(new InputStreamReader(TestCsvUserTestReader.class.getResourceAsStream("/test1.csv")))) {
			UserTest userTest = userTestReader.makeUserTest("testName", reader);
			assertThat(userTest).usingRecursiveComparison().isEqualTo(expectedUserTest);
		}
	}
}
