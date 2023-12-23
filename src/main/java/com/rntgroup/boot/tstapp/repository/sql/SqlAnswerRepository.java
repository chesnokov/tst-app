package com.rntgroup.boot.tstapp.repository.sql;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.repository.AnswerRepository;
import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import lombok.Setter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@BPPBenchmark
@Repository
@Setter
public class SqlAnswerRepository implements AnswerRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public SqlAnswerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public List<Answer> findAll() {
		return namedParameterJdbcTemplate.query("select qu_id, text, is_correct from answer order by qu_id, seq",
						(rs, rowNum) -> new Answer(
								rs.getString("qu_id"),
								rs.getString("text"),
								rs.getBoolean("is_correct")));
	}

	@Override
	public List<Answer> findByQuestionId(Question question) {
		return namedParameterJdbcTemplate.query("select qu_id, text, is_correct from answer " +
				"where qu_id = :id order by seq",
				new BeanPropertySqlParameterSource(question),
				(rs, rowNum) -> new Answer(
						rs.getString("qu_id"),
						rs.getString("text"),
						rs.getBoolean("is_correct")));
	}
}
