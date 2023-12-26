package com.rntgroup.boot.tstapp.shell;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.boot.tstapp.repository.csv.CsvUserTestReader;
import com.rntgroup.boot.tstapp.repository.sql.SqlUserTestRepository;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.dao.DataAccessException;
import org.springframework.shell.command.CommandContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

@Component
public class ImportTestCommand implements Function<CommandContext, String> {

	private final CsvUserTestReader csvUserTestReader;
	private final SqlUserTestRepository userTestRepository;

	public ImportTestCommand(CsvUserTestReader csvUserTestReader,
			 SqlUserTestRepository userTestRepository) {
		this.csvUserTestReader = csvUserTestReader;
		this.userTestRepository = userTestRepository;
	}

	@Override
	public String apply(CommandContext commandContext) {
		String[] args = commandContext.getRawArgs();
		if(args.length < 1)
			throw new ShellException("user test <filename> required", 2);

		try {
			Path path = Paths.get(args[0]);
			try(InputStream is = Files.newInputStream(path);
				var isr = new InputStreamReader(is);
				var csvReader = new CSVReader(isr)) {

				UserTest userTest =  csvUserTestReader.makeUserTest(args[0], csvReader);
				userTest = userTestRepository.save(userTest);
				return String.format("User test '%s' imported in database with id=%d", args[0], userTest.getId());

			}
		} catch (InvalidPathException e) {
			throw new ShellException(String.format("Path '%s' is invalid", args[0]), e, 2);
		} catch(IOException e) {
			throw new ShellException(String.format("Error reading from '%s'", args[0]), e, 2);
		} catch (CsvValidationException e) {
			throw new  ShellException(String.format("Invalid csv structure of file '%s'", args[0]), e, 2);
		}
		catch(DataAccessException e) {
			throw new ShellException(String.format("Error saving user test '%s' to database", args[0]), e, 2);
		}
	}
}
