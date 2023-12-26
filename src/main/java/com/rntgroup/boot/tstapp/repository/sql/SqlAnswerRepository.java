package com.rntgroup.boot.tstapp.repository.sql;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.repository.AnswerRepository;
import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import lombok.Setter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
		return namedParameterJdbcTemplate.query(
				"select qa.question_id question_id, a.text text, a.is_correct is_correct " +
						"from answer a " +
						"join question_answers qa on qa.answer_id = a.answer_id",
						(rs, rowNum) -> new Answer(
								rs.getInt("question_id"),
								rs.getString("text"),
								rs.getBoolean("is_correct")));
	}

	@Override
	public List<Answer> findByQuestionId(Question question) {
		return namedParameterJdbcTemplate.query(
				"select qa.question_id question_id, a.text text, a.is_correct is_correct " +
						"from answer a " +
						"join question_answers qa on qa.answer_id = a.answer_id " +
						"where question_id = :id",
				new BeanPropertySqlParameterSource(question),
				(rs, rowNum) -> new Answer(
						rs.getInt("question_id"),
						rs.getString("text"),
						rs.getBoolean("is_correct")));
	}

	@Override
	public Answer save(Answer answer) {
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(
				"insert into answer (text, is_correct) " +
						"values(:text, :correct)",
				new BeanPropertySqlParameterSource(answer),
				generatedKeyHolder);


		Integer id = generatedKeyHolder.getKey() != null ? generatedKeyHolder.getKey().intValue() : null;
		answer.setId(id);

		namedParameterJdbcTemplate.update("insert into question_answers (question_id, answer_id) " +
						"values(:questionId, :id)",
				new BeanPropertySqlParameterSource(answer));

		return answer;
	}
}
