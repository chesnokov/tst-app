package com.rntgroup.boot.tstapp.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.repository.config.ExternalTestRepositoryConfig;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@BPPBenchmark
@Repository
public class ExternalUserTestRepository implements UserTestRepository {
	private final ExternalTestRepositoryConfig config;
	private final CsvUserTestReader userTestReader;

	@Autowired
	public ExternalUserTestRepository(ExternalTestRepositoryConfig externalTestRepositoryConfig,
									  CsvUserTestReader userTestReader) {
		this.config = externalTestRepositoryConfig;
		this.userTestReader = userTestReader;
	}

	public ExternalUserTestRepository(ExternalUserTestRepository other) {
		this.config = other.config;
		this.userTestReader = other.userTestReader;
	}

	public List<UserTest> findAll() throws UserTestRepositoryException {
		File directory = new File(config.getExternalDir());
		File[] files = directory.listFiles();

		return Arrays.stream(Optional.ofNullable(files).orElse(new File[0]))
				.filter(f -> f.getName().endsWith(config.getSuffix()))
				.map(this::makeUserTest)
				.collect(Collectors.toList());
	}

	@Override
	public List<UserTest> findAllLazy() {
		return findAll();
	}

	private UserTest makeUserTest(File file) throws UserTestRepositoryException {
		try(var fileReader = new FileReader(file);
			var csvReader = new CSVReader(fileReader)) {

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

