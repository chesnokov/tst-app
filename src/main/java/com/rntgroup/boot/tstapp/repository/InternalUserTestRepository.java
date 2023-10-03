package com.rntgroup.boot.tstapp.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@Qualifier("UserTestRepositories")
@Order(1)
public class InternalUserTestRepository implements UserTestRepository {
	private final String userTestDir;
	private final String userTestSuffix;
	private final CsvUserTestReader userTestReader;

	public InternalUserTestRepository(@Value("${test.internal.dir}") String userTestDir,
									  @Value("${test.suffix}") String userTestSuffix,
									  CsvUserTestReader userTestReader) {
		this.userTestDir = userTestDir;
		this.userTestSuffix = userTestSuffix;
		this.userTestReader = userTestReader;
	}

	public List<UserTest> findAll() {
		String jarName = getJarName();

		if(jarName.endsWith(".jar")) {
			return getUserTestsFromJarFile(jarName);
		}
		return getUserTestsFromFileSystem();
	}

	private String getJarName() {
		String resourceDirectory = String.format("/%s",userTestDir);
		URL resource = InternalUserTestRepository.class.getResource(resourceDirectory);
		if(isNull(resource)) {
			throw new UserTestRepositoryException(
					MessageFormat.format("User tests directory '{0}' not found", resourceDirectory));
		}
		String fileName = resource.getFile();
		return fileName.replaceAll("(!.*|file:/)", "");
	}

	private List<UserTest> getUserTestsFromJarFile(String jarName) {
		List<UserTest> tstFiles = new ArrayList<>();
		try (JarFile jf = new JarFile(jarName)){

			Enumeration<JarEntry> entries = jf.entries();
			while (entries.hasMoreElements()) {
				JarEntry je = entries.nextElement();
				if (je.getName().contains(userTestDir+"/") &&
						je.getName().endsWith(userTestSuffix)) {
					String filePath = je.getName().replaceAll("BOOT-INF/classes/", "");
					tstFiles.add(makeUserTest(String.format("/%s",filePath)));
				}
			}
		} catch (IOException e) {
			throw new UserTestRepositoryException(MessageFormat.format("Error reading user test files list from {0}", jarName), e);
		}
		return tstFiles;
	}

	private List<UserTest> getUserTestsFromFileSystem() {
		String resourceDirectory = String.format("/%s", userTestDir);
		URL resource = InternalUserTestRepository.class.getResource(resourceDirectory);
		if(resource == null) {
			throw new UserTestRepositoryException(MessageFormat.format("No directory {0} in application resource", resourceDirectory));
		}
		File directory = new File(resource.getFile());
		File[] files = directory.listFiles();

		return Arrays.stream(Optional.ofNullable(files).orElse(new File[0]))
				.filter(f -> f.getName().endsWith(userTestSuffix))
				.map(f -> String.format("/%s/%s", userTestDir,f.getName()))
				.map(this::makeUserTest)
				.collect(Collectors.toList());
	}

	private UserTest makeUserTest(String path) {
		InputStream inputStream = InternalUserTestRepository.class.getResourceAsStream(path);
		if(inputStream == null) {
			throw new UserTestRepositoryException(MessageFormat.format("User test file in resource {0} not found", path));
		}
		try(InputStream is =  inputStream;
			InputStreamReader isr = new InputStreamReader(is);
			CSVReader csvReader = new CSVReader(isr)) {
			return userTestReader.makeUserTest(path, csvReader);
		} catch (IOException e) {
			throw new UserTestRepositoryException(MessageFormat.format("Error reading user test file from internal resource {0}", path), e);
		} catch (CsvValidationException e) {
			throw new UserTestRepositoryException(MessageFormat.format("Error in csv structure of user test file {0}", path), e);
		}
	}

}

