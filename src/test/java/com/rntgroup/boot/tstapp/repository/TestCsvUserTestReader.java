package com.rntgroup.boot.tstapp.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.tstapp.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class TestCsvUserTestReader {
	@Autowired
	private CsvUserTestReader userTestReader;

	@Test
	public void shouldMakeEmptyUserTest() {

		var thrown = assertThrows(CsvValidationException.class, () ->{

			UserTest expectedUserTest = new UserTest("testName", new ArrayList<>());

			try(CSVReader reader = new CSVReader(new InputStreamReader(TestCsvUserTestReader.class.getResourceAsStream("/test2.csv")))) {
				UserTest userTest = userTestReader.makeUserTest("testName", reader);
				assertThat(userTest).usingRecursiveComparison().isEqualTo(expectedUserTest);
			}

		});

		assertThat(thrown.getMessage()).isEqualTo("user test csv file 'testName' has no content");
	}

	@Test
	public void shouldMakeUserTest() throws IOException, CsvValidationException {

		UserTest expectedUserTest = UserTestUtil.getUserTest();

		try(CSVReader reader = new CSVReader(new InputStreamReader(TestCsvUserTestReader.class.getResourceAsStream("/test1.csv")))) {
			UserTest userTest = userTestReader.makeUserTest("testName", reader);
			assertThat(userTest).usingRecursiveComparison().isEqualTo(expectedUserTest);
		}
	}
}
