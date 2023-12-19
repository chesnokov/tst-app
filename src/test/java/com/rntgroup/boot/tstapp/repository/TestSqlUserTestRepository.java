package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.tstapp.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"/schema.sql", "/test-data.sql"})
public class TestSqlUserTestRepository {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Test
	public void shouldReturnUserTestsFromDatabase() {
		SqlUserTestRepository userTestRepository = getUserTestRepository();

		List<UserTest> userTests = userTestRepository.findAll();
		assertThat(userTests).usingRecursiveComparison().isEqualTo(UserTestUtil.getUserTests());
	}

	private SqlUserTestRepository getUserTestRepository() {
		SqlAnswerRepository answerRepository = new SqlAnswerRepository();
		answerRepository.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
		SqlQuestionRepository questionRepository = new SqlQuestionRepository();
		questionRepository.setAnswerRepository(answerRepository);
		questionRepository.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
		SqlUserTestRepository userTestRepository = new SqlUserTestRepository();
		userTestRepository.setQuestionRepository(questionRepository);
		userTestRepository.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
		return userTestRepository;
	}
}
