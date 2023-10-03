package com.rntgroup.boot.tstapp.service;

import java.util.Optional;

public interface InputOutputService {
	void print(String str);
	void println(String str);
	String getUserInput(String text);
	Optional<Integer> getUserInputAsInt(String text);
}
