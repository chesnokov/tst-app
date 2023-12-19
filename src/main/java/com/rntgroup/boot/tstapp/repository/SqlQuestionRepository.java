package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
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
public class SqlQuestionRepository implements QuestionRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private AnswerRepository answerRepository;

	@Override
	public List<Question> findAll() {
		List<Question> questions =
				namedParameterJdbcTemplate.query( "select qu_id, ex_id, text from question order by ex_id, seq",
					(rs, rowNum) -> new Question(
							rs.getString("qu_id"),
							rs.getString("ex_id"),
							rs.getString("text")));

		return joinQuestionsAndAnswers(questions, answerRepository.findAll());
	}

	private List<Question> joinQuestionsAndAnswers(List<Question> questions, List<Answer> answers) {
		Map<String, List<Answer>> answersByQuestionId = groupAnswersByQuestionId(answers);
		questions.forEach(question -> question.setAnswers(answersByQuestionId.get(question.getId())));
		return questions;
	}

	private Map<String, List<Answer>> groupAnswersByQuestionId(List<Answer> answers) {
		return answers.stream().collect(Collectors.groupingBy(Answer::getQuestionId,
				HashMap::new, Collectors.toCollection(ArrayList::new)));
	}
}
