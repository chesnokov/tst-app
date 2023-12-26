package com.rntgroup.boot.tstapp.repository.sql;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.repository.AnswerRepository;
import com.rntgroup.boot.tstapp.repository.QuestionRepository;
import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.tstapp.test.sql.LazySqlQuestion;
import lombok.Setter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@BPPBenchmark
@Repository
@Setter
public class SqlQuestionRepository implements QuestionRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final AnswerRepository answerRepository;

	public SqlQuestionRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
			 AnswerRepository answerRepository) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.answerRepository = answerRepository;
	}


	@Override
	public List<Question> findAll() {
		List<Question> questions =
				namedParameterJdbcTemplate.query(
						"select q.question_id question_id, qs.test_id test_id,  q.text text " +
								"from question q " +
								"join user_test_questions qs on qs.question_id = q.question_id",
					(rs, rowNum) -> new Question(
							rs.getInt("question_id"),
							rs.getInt("test_id"),
							rs.getString("text")));

		return joinQuestionsAndAnswers(questions, answerRepository.findAll());
	}

	@Override
	public List<Question> findByUserTestId(UserTest userTest) {
		return namedParameterJdbcTemplate.query(
				"select q.question_id question_id, qs.test_id test_id,  q.text text " +
						"from question q " +
						"join user_test_questions qs on qs.question_id = q.question_id " +
						"where qs.test_id = :id",
				new BeanPropertySqlParameterSource(userTest),
			(rs, rowNum) -> new LazySqlQuestion(
				answerRepository,
				rs.getInt("question_id"),
				rs.getInt("test_id"),
				rs.getString("text")));
	}

	@Override
	public Question save(Question question) {
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update("insert into question (text) values(:text)",
				new BeanPropertySqlParameterSource(question),
				generatedKeyHolder);

		Integer id = generatedKeyHolder.getKey() != null ? generatedKeyHolder.getKey().intValue() : null;
		question.setId(id);

		namedParameterJdbcTemplate.update("insert into user_test_questions (test_id, question_id) " +
				"values(:testId, :id)",
				new BeanPropertySqlParameterSource(question));

		var answers = question.getAnswers();
		if(answers != null) {
			answers.forEach(answerRepository::save);
		}

		return question;
	}

	private List<Question> joinQuestionsAndAnswers(List<Question> questions, List<Answer> answers) {
		Map<Integer, List<Answer>> answersByQuestionId = groupAnswersByQuestionId(answers);
		questions.forEach(question -> question.setAnswers(answersByQuestionId.get(question.getId())));
		return questions;
	}

	private Map<Integer, List<Answer>> groupAnswersByQuestionId(List<Answer> answers) {
		return answers.stream().collect(Collectors.groupingBy(Answer::getQuestionId,
				HashMap::new, Collectors.toCollection(ArrayList::new)));
	}
}
