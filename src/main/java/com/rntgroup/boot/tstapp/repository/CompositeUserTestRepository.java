package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@BPPBenchmark
@Repository
public class CompositeUserTestRepository implements UserTestRepository {

	private List<UserTestRepository> repositories;

	@Autowired
	public CompositeUserTestRepository(List<UserTestRepository> repositories) {
		this.repositories = repositories;
	}

	public CompositeUserTestRepository(CompositeUserTestRepository other) {
		this.repositories = other.repositories;
	}

	public List<UserTest> findAll()  {
		anotherMethodToBeLoggedByCglibProxy();
		return repositories.stream()
				.map(UserTestRepository::findAll)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	public void anotherMethodToBeLoggedByCglibProxy() {
	}

}
