package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.test.UserTestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SqlUserTestResultRepository implements UserTestResultRepository {
	private static final String FIND_ALL_TEST_RESULT = "select re_id, ex_name, correct_count, ans_count" +
			" from result";
	private static final String INSERT_TEST_RESULT = "insert into result " +
			"(ex_name, correct_count, ans_count)" +
			"values(:exName, :correctCount, :ansCount)";

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserTestResult> findAll() {
		return namedParameterJdbcTemplate.query(FIND_ALL_TEST_RESULT, (rs, rowNum)->
				new UserTestResult(rs.getLong(1),
						rs.getString(2),
						rs.getInt(3),
						rs.getInt(4)));
	}

	@Transactional
	@Override
	public UserTestResult save(UserTestResult result) {
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("exName", result.getTestName());
		parameterSource.addValue("correctCount", result.getCorrectAnswers());
		parameterSource.addValue("ansCount", result.getAnswersCount());
		namedParameterJdbcTemplate.update(INSERT_TEST_RESULT, parameterSource, generatedKeyHolder);
		long resultId = generatedKeyHolder.getKey() != null ? generatedKeyHolder.getKey().longValue() : 0;
		result.setId(resultId);
		return result;
	}
}
