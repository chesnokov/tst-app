package com.rntgroup.boot.tstapp.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Qualifier("UserTestRepositories")
@Order(2)
public class ExternalUserTestRepository implements UserTestRepository {
	private final String userTestDir;
	private final String userTestSuffix;
	private final CsvUserTestReader userTestReader;

	public ExternalUserTestRepository(@Value("${test.external.dir}") String userTestDir,
									  @Value("${test.suffix}") String userTestSuffix,
									  CsvUserTestReader userTestReader) {
		this.userTestDir = userTestDir;
		this.userTestSuffix = userTestSuffix;
		this.userTestReader = userTestReader;
	}

	public List<UserTest> findAll() throws UserTestRepositoryException {
		File directory = new File(userTestDir);
		File[] files = directory.listFiles();

		return Arrays.stream(Optional.ofNullable(files).orElse(new File[0]))
				.filter(f -> f.getName().endsWith(userTestSuffix))
				.map(this::makeUserTest)
				.collect(Collectors.toList());
	}

	private UserTest makeUserTest(File file) throws UserTestRepositoryException {
		try(CSVReader csvReader = new CSVReader(new FileReader(file))) {
			return userTestReader.makeUserTest(file.toString(),csvReader);
		} catch (FileNotFoundException e) {
			throw new UserTestRepositoryException(MessageFormat.format("User test not found {0}", file), e);
		} catch (IOException e) {
			throw new UserTestRepositoryException(MessageFormat.format("Error reading user test file {0}", file), e);
		} catch (CsvValidationException e) {
			throw new UserTestRepositoryException(MessageFormat.format("Error in csv structure of user test file {0}", file), e);
		}
	}
}

