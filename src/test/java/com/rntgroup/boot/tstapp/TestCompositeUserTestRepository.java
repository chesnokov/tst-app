package com.rntgroup.boot.tstapp;

import com.rntgroup.boot.tstapp.repository.CompositeUserTestRepository;
import com.rntgroup.boot.tstapp.repository.ExternalUserTestRepository;
import com.rntgroup.boot.tstapp.repository.InternalUserTestRepository;
import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class TestCompositeUserTestRepository {
	@Autowired
	CompositeUserTestRepository compositeUserTestRepository;
	@MockBean
	InternalUserTestRepository internalUserTestRepository;
	@MockBean
	ExternalUserTestRepository externalUserTestRepository;

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

	@Test
	public void shouldReturnEmptyList() {
		List<UserTest> tests = compositeUserTestRepository.findAll();
		assertThat(tests.size()).isEqualTo(0);
	}

	@Test
	public void shouldReturnCombinedListOfUserTests() {
		when(internalUserTestRepository.findAll())
				.thenReturn(Collections.singletonList(getUserTest()));
		when(externalUserTestRepository.findAll())
				.thenReturn(Collections.singletonList(getUserTest()));

		List<UserTest> tests = compositeUserTestRepository.findAll();

		assertThat(tests).usingRecursiveComparison()
				.isEqualTo(Arrays.asList(getUserTest(), getUserTest()));
	}

}
