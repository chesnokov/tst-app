package com.rntgroup.boot.tstapp.repository.sql;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.repository.UserTestResultRepository;
import com.rntgroup.boot.tstapp.test.UserTestResult;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@BPPBenchmark
@Repository
public class SqlUserTestResultRepository implements UserTestResultRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public SqlUserTestResultRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public List<UserTestResult> findAll() {
		return namedParameterJdbcTemplate.query(
				"select result_id, user_test_name, correct_answer_count, question_count, date_time " +
						"from result",
				(rs, rowNum)->
				new UserTestResult(rs.getLong("result_id"),
						rs.getString("user_test_name"),
						rs.getInt("correct_answer_count"),
						rs.getInt("question_count"),
						rs.getTimestamp("date_time")));
	}

	@Transactional
	@Override
	public UserTestResult save(UserTestResult result) {
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update("insert into result (user_test_name, question_count, correct_answer_count, date_time) " +
						"values(:testName,  :questionsCount, :correctAnswersCount, :timestamp)",
				new BeanPropertySqlParameterSource(result), generatedKeyHolder);

		long resultId = generatedKeyHolder.getKey() != null ? generatedKeyHolder.getKey().longValue() : 0;
		result.setId(resultId);
		return result;
	}
}
