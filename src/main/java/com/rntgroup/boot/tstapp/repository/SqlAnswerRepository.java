package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.annotation.AspectJBenchmark;
import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.test.Answer;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@BPPBenchmark
@Repository
@Setter
public class SqlAnswerRepository implements AnswerRepository {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Answer> findAll() {
		return namedParameterJdbcTemplate.query("select qu_id, text, is_correct from answer order by qu_id, seq",
						(rs, rowNum) -> new Answer(
								rs.getString("qu_id"),
								rs.getString("text"),
								rs.getBoolean("is_correct")));
	}
}
