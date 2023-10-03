package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("CompositeUserTestRepository")
public class CompositeUserTestRepository implements UserTestRepository {

	private final List<UserTestRepository> repositories;

	public CompositeUserTestRepository(@Qualifier("UserTestRepositories") List<UserTestRepository> repositories) {
		this.repositories = repositories;
	}

	public List<UserTest> findAll()  {
		List<UserTest> userTests = new ArrayList<>();
		for(UserTestRepository repository:repositories) {
			userTests.addAll(repository.findAll());
		}
		return userTests;
	}

}
