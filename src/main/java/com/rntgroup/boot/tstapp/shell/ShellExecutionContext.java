package com.rntgroup.boot.tstapp.shell;

import com.rntgroup.boot.tstapp.test.Answer;
import com.rntgroup.boot.tstapp.test.Question;
import com.rntgroup.boot.tstapp.test.UserTest;
import com.rntgroup.boot.tstapp.test.UserTestResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Supplier;

@Component
@Getter
@Setter
public class ShellExecutionContext {
	private final Supplier<Timestamp> timestampSupplier;
	private List<UserTest> userTests;
	private UserTest runningTest;
	private int questionIndex;
	private int correctAnswersCount;

	public ShellExecutionContext(Supplier<Timestamp> timestampSupplier) {
		this.timestampSupplier = timestampSupplier;
	}

	public List<Answer> getCurrentQuestionAnswers() {
		List<Question> questions = runningTest.getQuestions();
		Question question = questions.get(questionIndex);
		return question.getAnswers();
	}

	public Question getCurrentQuestion() {
		return runningTest.getQuestions().get(questionIndex);
	}

	public void setCurrentTest(UserTest userTest) {
		if (userTest != null) {
			this.runningTest = userTest;
			questionIndex = 0;
			correctAnswersCount = 0;
		} else {
			questionIndex = 0;
			runningTest = null;
			correctAnswersCount = 0;
		}
	}

	public void incrementCorrectAnswersCount() {
		correctAnswersCount++;
	}

	public boolean hasNextQuestion() {
		return questionIndex < runningTest.getQuestions().size() - 1;
	}

	public Question nextQuestion() {
		if(questionIndex < runningTest.getQuestions().size() - 1) {
			questionIndex ++;
		}
		return runningTest.getQuestions().get(questionIndex);
	}

	public UserTestResult getUserTestResult() {
		return new UserTestResult(
				runningTest.getName(),
				correctAnswersCount,
				runningTest.getQuestions().size(),
				timestampSupplier.get());
	}
}
