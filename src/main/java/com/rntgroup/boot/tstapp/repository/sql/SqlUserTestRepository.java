package com.rntgroup.boot.tstapp.repository.sql;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.repository.QuestionRepository;
import com.rntgroup.boot.tstapp.repository.UserTestRepository;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.tstapp.test.sql.LazySqlUserTest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@BPPBenchmark
@Repository
@Setter
public class SqlUserTestRepository implements UserTestRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public List<UserTest> findAll() {
		List<UserTest> userTests =
			namedParameterJdbcTemplate.query("select ex_id, name from exercise",
					(rs, rowNum) -> new UserTest(rs.getString("ex_id"),
							rs.getString("name")));

		return joinTestsWithQuestions(userTests, questionRepository.findAll());
	}

	@Override
	public List<UserTest> findAllLazy() {
		return namedParameterJdbcTemplate.query("select ex_id, name from exercise",
				(rs, rowNum) -> new LazySqlUserTest(questionRepository,
						rs.getString("ex_id"),
						rs.getString("name")));
	}

	private List<UserTest> joinTestsWithQuestions(List<UserTest> userTests, List<Question> questions) {
		Map<String, List<Question>> questionsByTestId = groupQuestionsByTestId(questions);
		userTests.forEach(userTest -> userTest.setQuestions(questionsByTestId.get(userTest.getId())));
		return userTests;
	}

	private Map<String, List<Question>> groupQuestionsByTestId(List<Question> questions) {
		return questions.stream().collect(Collectors.groupingBy(Question::getTestId,
				HashMap::new, Collectors.toCollection(ArrayList::new)));
	}
}
