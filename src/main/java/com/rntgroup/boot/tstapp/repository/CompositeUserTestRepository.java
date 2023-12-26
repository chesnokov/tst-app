package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import com.rntgroup.boot.tstapp.test.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
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

	@Override
	public List<UserTest> findAll()  {
		return findAll(UserTestRepository::findAll);
	}

	@Override
	public List<UserTest> findAllLazy() {
		return findAll(UserTestRepository::findAllLazy);
	}

	@Override
	public UserTest save(UserTest userTest) {
		throw new UnsupportedOperationException("Operation save() not supported on CompositeUserTestRepository");
	}

	private List<UserTest> findAll(Function<UserTestRepository,List<UserTest>> mapper) {
		return repositories.stream()
				.map(mapper)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
}
