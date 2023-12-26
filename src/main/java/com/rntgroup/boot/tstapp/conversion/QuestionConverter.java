package com.rntgroup.boot.tstapp.conversion;

import com.rntgroup.boot.tstapp.test.Question;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class QuestionConverter implements Converter<Question, String> {
	@Override
	public String convert(Question question) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(question.getText()).append("\n");
		for(int i=0; i<question.getAnswers().size(); i++) {
			stringBuilder.append(i + 1).append(". ").append(question.getAnswers().get(i).getText()).append("\n");
		}
		return stringBuilder.toString();
	}
}
