package com.rntgroup.boot.repository;

import com.rntgroup.boot.tstapp.repository.CompositeUserTestRepository;
import com.rntgroup.boot.tstapp.repository.ExternalUserTestRepository;
import com.rntgroup.boot.tstapp.repository.InternalUserTestRepository;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.util.UserTestUtil;
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
	@Autowired
	CompositeUserTestRepository compositeUserTestRepository;
	@MockBean
	InternalUserTestRepository internalUserTestRepository;
	@MockBean
	ExternalUserTestRepository externalUserTestRepository;

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

		List<UserTest> tests = compositeUserTestRepository.findAll();

		assertThat(tests).usingRecursiveComparison()
				.isEqualTo(Arrays.asList(UserTestUtil.getUserTest(), UserTestUtil.getUserTest()));
	}

}
