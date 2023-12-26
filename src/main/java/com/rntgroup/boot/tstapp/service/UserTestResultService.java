package com.rntgroup.boot.tstapp.service;

import com.rntgroup.boot.tstapp.repository.UserTestResultRepository;
import com.rntgroup.boot.tstapp.test.UserTestResult;
import org.springframework.stereotype.Service;

@Service
public class UserTestResultService {
	private final UserTestResultRepository userTestResultRepository;

	public UserTestResultService(UserTestResultRepository userTestResultRepository) {
		this.userTestResultRepository = userTestResultRepository;
	}

	public void processResult(UserTestResult result) {
		userTestResultRepository.save(result);
	}
}
