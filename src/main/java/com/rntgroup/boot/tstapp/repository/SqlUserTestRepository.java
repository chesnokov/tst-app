package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.annotation.AspectJBenchmark;
import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SqlUserTestRepository implements  UserTestRepository {
	private static final String FIND_ALL_TESTS = "select ex_id, name from exercise";
	private static final String FIND_QUESTIONS_BY_TEST_ID = "select qu_id, text from question where ex_id=:id order by seq";
	private static final String FIND_ANSWERS_BY_QUESTION_ID = "select text, is_correct from answer where qu_id=:id order by seq";

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@AspectJBenchmark
	@Override
	public List<UserTest> findAll() {
		List<UserTest> userTests =
			namedParameterJdbcTemplate.query(FIND_ALL_TESTS,
					(rs, rowNum) -> new UserTest(rs.getString(1), rs.getString(2)));

		for(UserTest userTest: userTests) {

			List<Question> questions =
				namedParameterJdbcTemplate.query( FIND_QUESTIONS_BY_TEST_ID,
						new BeanPropertySqlParameterSource(userTest),
					 (rs, rowNum) -> new Question(rs.getString(1), rs.getString(2)));
			userTest.setQuestions(questions);

			for(Question question: questions) {
				List<Answer> answers =
					namedParameterJdbcTemplate.query(FIND_ANSWERS_BY_QUESTION_ID,
							new BeanPropertySqlParameterSource(question),
						(rs, rowNum) -> new Answer(rs.getString(1), rs.getBoolean(2)));
				question.setAnswers(answers);
			}
		}
		return userTests;
	}
}
