package com.rntgroup.tstapp.beans;

import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.tstapp.tst.Question;
import com.rntgroup.tstapp.tst.Tst;
import com.rntgroup.tstapp.tst.file.TstFile;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Application {
	private InternalTstDir internalTstDir;
	private ExternalTstDir externalTstDir;

	public Application(InternalTstDir internalTstDir, ExternalTstDir externalTstDir) {
		this.internalTstDir = internalTstDir;
		this.externalTstDir = externalTstDir;
	}

	public void run() throws IOException, CsvValidationException {
		List<TstFile> testFiles = getTestFiles();
		for(;;) {
			for(int i=0; i<testFiles.size(); i++) {
				System.out.println("" + i + ". " + testFiles.get(i).getName());
			}
			String input = getUserInput("Choose test: ");
			if(input.startsWith("q")) {
				break;
			} else {
				runTest(testFiles, Integer.parseInt(input));
			}
		}

	}

	private void runTest(List<TstFile> testFiles, int index) throws IOException, CsvValidationException {
		if(index < 0 || index >= testFiles.size())  {
			return;
		}
		Tst tst = testFiles.get(index).getTst();
		int correctCount = 0;
		for(Question question: tst.getQuestions()) {
			boolean questionResult = getQuestionResult(question);
			if(questionResult) {
				correctCount++;
			}
		}
		System.out.println("Result: " + correctCount + " of " + tst.getQuestions().size());
	}

	private boolean getQuestionResult(Question question) {
		System.out.println(question.getText());
		for(int i=0; i<question.getAnswers().size(); i++) {
			System.out.println("" + i + ". " + question.getAnswers().get(i).getText());
		}
		String input = getUserInput("Answer: ");
		int answerIndex = Integer.parseInt(input);
		if (answerIndex >= 0 && answerIndex < question.getAnswers().size()) {
			return question.getAnswers().get(answerIndex).isCorrect();
		} else {
			return false;
		}
	}

	private List<TstFile> getTestFiles() throws IOException {
		List<TstFile> files = internalTstDir.getTstFiles();
		files.addAll(externalTstDir.getTstFiles());
		return files;
	}

	private String getUserInput(String text) {
		System.out.print(text);
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine().trim();
	}

}
