package com.rntgroup.boot.tstapp.repository.sql;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.repository.UserTestResultRepository;
import com.rntgroup.boot.tstapp.test.UserTestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@BPPBenchmark
@Repository
public class SqlUserTestResultRepository implements UserTestResultRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserTestResult> findAll() {
		return namedParameterJdbcTemplate.query("select re_id, ex_name, correct_count, ans_count, date_time from result",
				(rs, rowNum)->
				new UserTestResult(rs.getLong("re_id"),
						rs.getString("ex_name"),
						rs.getInt("correct_count"),
						rs.getInt("ans_count"),
						rs.getTimestamp("date_time")));
	}

	@Transactional
	@Override
	public UserTestResult save(UserTestResult result) {
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update("insert into result (ex_name, correct_count, ans_count, date_time) " +
						"values(:testName, :correctAnswers, :answersCount, :timestamp)",
				new BeanPropertySqlParameterSource(result), generatedKeyHolder);

		long resultId = generatedKeyHolder.getKey() != null ? generatedKeyHolder.getKey().longValue() : 0;
		result.setId(resultId);
		return result;
	}
}
