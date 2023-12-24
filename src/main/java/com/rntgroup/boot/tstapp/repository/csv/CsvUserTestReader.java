package com.rntgroup.boot.tstapp.repository.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvUserTestReader {

	private final ConversionService conversionService;

	public CsvUserTestReader(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	public UserTest makeUserTest(String name, CSVReader csvReader) throws IOException, CsvValidationException {
		String userTestName = readUserTestName(name, csvReader);
		List<Question> questions = readQuestions( name, csvReader);
		return new UserTest(userTestName, questions);
	}

	private String readUserTestName(String name, CSVReader csvReader) throws IOException, CsvValidationException {
		String [] values = csvReader.readNext();
		if(values == null || values.length == 0) {
			throw new CsvValidationException(String.format("user test csv file '%s' has no content", name));
		}
		return values[0];
	}

	private List<Question> readQuestions(String name, CSVReader csvReader) throws IOException, CsvValidationException {
		List<Question> questions = new ArrayList<>();
		for(String [] values = csvReader.readNext(); values != null; values = csvReader.readNext()) {
			var question = conversionService.convert(values, Question.class);
			questions.add(question);
		}
		return questions;
	}
}
