package com.rntgroup.boot.tstapp.repository;

import com.rntgroup.boot.tstapp.test.UserTestResult;

import java.util.List;

public interface UserTestResultRepository {
	List<UserTestResult> findAll();
	UserTestResult save(UserTestResult result);
}
