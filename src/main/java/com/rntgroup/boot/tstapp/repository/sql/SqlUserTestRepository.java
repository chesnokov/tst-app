package com.rntgroup.boot.tstapp.repository.sql;

import com.rntgroup.boot.tstapp.repository.QuestionRepository;
import com.rntgroup.boot.tstapp.repository.UserTestRepository;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.tstapp.test.sql.LazySqlUserTest;
import lombok.Setter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Setter
public class SqlUserTestRepository implements UserTestRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final QuestionRepository questionRepository;

	public SqlUserTestRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
			 QuestionRepository questionRepository) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.questionRepository = questionRepository;
	}

	@Override
	public List<UserTest> findAll() {
		List<UserTest> userTests =
			namedParameterJdbcTemplate.query("select test_id, name from user_test",
					(rs, rowNum) -> new UserTest(rs.getInt("test_id"),
							rs.getString("name")));

		return joinTestsWithQuestions(userTests, questionRepository.findAll());
	}

	@Override
	public List<UserTest> findAllLazy() {
		return namedParameterJdbcTemplate.query("select test_id, name from user_test",
				(rs, rowNum) -> new LazySqlUserTest(questionRepository,
						rs.getInt("test_id"),
						rs.getString("name")));
	}

	@Transactional
	@Override
	public UserTest save(UserTest userTest) {
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update("insert into user_test (name) values(:name)",
				new BeanPropertySqlParameterSource(userTest),
				generatedKeyHolder);

		Integer id = generatedKeyHolder.getKey() != null ? generatedKeyHolder.getKey().intValue() : null;
		userTest.setId(id);

		var questions = userTest.getQuestions();
		if(questions != null) {
			questions.forEach(questionRepository::save);
		}

		return userTest;
	}

	private List<UserTest> joinTestsWithQuestions(List<UserTest> userTests, List<Question> questions) {
		Map<Integer, List<Question>> questionsByTestId = groupQuestionsByTestId(questions);
		userTests.forEach(userTest -> userTest.setQuestions(questionsByTestId.get(userTest.getId())));
		return userTests;
	}

	private Map<Integer, List<Question>> groupQuestionsByTestId(List<Question> questions) {
		return questions.stream().collect(Collectors.groupingBy(Question::getTestId,
				HashMap::new, Collectors.toCollection(ArrayList::new)));
	}
}
