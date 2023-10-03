package com.rntgroup.boot.tstapp.conversion;

import com.rntgroup.boot.tstapp.test.UserTestResult;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserTestResultConverter implements Converter<UserTestResult, String> {
	@Override
	public String convert(UserTestResult result) {
		return "Result: " + result.getCorrectAnswers() + " of " + result.getAnswersCount();
	}
}
