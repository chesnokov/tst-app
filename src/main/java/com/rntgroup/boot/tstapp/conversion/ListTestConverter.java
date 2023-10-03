package com.rntgroup.boot.tstapp.conversion;

import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListTestConverter implements Converter<List<UserTest>, String> {
	@Override
	public String convert(List<UserTest> tests) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i=0; i<tests.size(); i++) {
			stringBuilder.append((i+1)).append(". ").append(tests.get(i).getName()).append("\n");
		}
		return stringBuilder.toString();
	}
}
