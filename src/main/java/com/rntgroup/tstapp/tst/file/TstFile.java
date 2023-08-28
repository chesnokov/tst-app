package com.rntgroup.tstapp.tst.file;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.tstapp.tst.Answer;
import com.rntgroup.tstapp.tst.Question;
import com.rntgroup.tstapp.tst.Tst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class TstFile {
	public abstract Tst getTst() throws IOException, CsvValidationException;
	public abstract String getName();

	protected Tst readCsv(CSVReader csvReader) throws IOException, CsvValidationException {
		List<Question> questions = new ArrayList<>();
		String [] values;
		while((values = csvReader.readNext()) != null) {
			String questionText = values[0];
			List<Answer> answers = new ArrayList<>();
			for(int i=1; i<values.length-1; i+=2) {
				Answer answer = new Answer(values[i], values[i+1].trim().equals("true"));
				answers.add(answer);
			}
			questions.add(new Question(questionText, answers));
		}
		return new Tst(questions);

	}
}
