package com.rntgroup.boot.tstapp.conversion;

import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionFromStringsConverter implements Converter<String [], Question> {
	@Override
	public Question convert(String[] source) {
		return new Question( source[0], convertAnswers(source));
	}

	private List<Answer> convertAnswers(String[] source) {
		List<Answer> answers = new ArrayList<>();
		for(int i = 1; i < source.length - 1; i += 2) {
			var answer = new Answer(null, source[i], source[i+1].trim().equals("true"));
			answers.add(answer);
		}
		return answers;
	}
}
