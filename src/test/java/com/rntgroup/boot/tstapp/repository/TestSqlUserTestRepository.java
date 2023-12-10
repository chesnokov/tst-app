package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.application.CommandLineUserTestRunner;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.tstapp.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestSqlUserTestRepository {
	@MockBean
	CommandLineUserTestRunner runner;
	@Autowired
	SqlUserTestRepository repository;
	@Test
	public void shouldReturnUserTestsFromDatabase() {
		List<UserTest> userTests = repository.findAll();
		assertThat(userTests).usingRecursiveComparison().isEqualTo(UserTestUtil.getUserTests());
	}
}
