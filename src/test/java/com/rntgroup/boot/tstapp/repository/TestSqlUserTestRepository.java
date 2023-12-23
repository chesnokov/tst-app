package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.repository.sql.SqlAnswerRepository;
import com.rntgroup.boot.tstapp.repository.sql.SqlQuestionRepository;
import com.rntgroup.boot.tstapp.repository.sql.SqlUserTestRepository;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.tstapp.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ComponentScan(basePackages = {"com.rntgroup.boot.tstapp.repository.sql"})
@Sql({"/schema.sql", "/test-data.sql"})
public class TestSqlUserTestRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private SqlUserTestRepository userTestRepository;

	@Test
	public void shouldReturnUserTestsFromDatabase() {
		List<UserTest> userTests = userTestRepository.findAll();
		assertThat(userTests).usingRecursiveComparison().isEqualTo(UserTestUtil.getUserTests());
	}
}
