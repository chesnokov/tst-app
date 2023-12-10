package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@BPPBenchmark
@Repository
public class CompositeUserTestRepository implements UserTestRepository {

	@Autowired
	private List<UserTestRepository> repositories;

	public List<UserTest> findAll()  {
		List<UserTest> userTests = new ArrayList<>();
		for(UserTestRepository repository:repositories) {
			userTests.addAll(repository.findAll());
		}
		return userTests;
	}

}
