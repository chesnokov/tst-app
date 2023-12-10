package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.application.CommandLineUserTestRunner;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.tstapp.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes=CompositeUserTestRepository.class)
public class TestCompositeUserTestRepository {
	@MockBean
	CommandLineUserTestRunner runner;
	@Autowired
	CompositeUserTestRepository compositeUserTestRepository;
	@MockBean
	InternalUserTestRepository internalUserTestRepository;
	@MockBean
	ExternalUserTestRepository externalUserTestRepository;
	@MockBean
	SqlUserTestRepository sqlUserTestRepository;

	@Test
	public void shouldReturnEmptyList() {
		List<UserTest> tests = compositeUserTestRepository.findAll();
		assertThat(tests.size()).isEqualTo(0);
	}

	@Test
	public void shouldReturnCombinedListOfUserTests() {
		when(internalUserTestRepository.findAll())
				.thenReturn(Collections.singletonList(UserTestUtil.getUserTest()));
		when(externalUserTestRepository.findAll())
				.thenReturn(Collections.singletonList(UserTestUtil.getUserTest()));
		when(sqlUserTestRepository.findAll())
				.thenReturn(Collections.singletonList(UserTestUtil.getUserTest()));

		List<UserTest> tests = compositeUserTestRepository.findAll();

		assertThat(tests).usingRecursiveComparison()
				.isEqualTo(Arrays.asList(UserTestUtil.getUserTest(), UserTestUtil.getUserTest(), UserTestUtil.getUserTest()));
	}

}
