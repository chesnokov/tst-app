package com.rntgroup.boot.tstapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

@Service
public class StreamInputOutputService implements InputOutputService {
	private final PrintStream out;
	private final InputStream in;

	public StreamInputOutputService(@Value("#{T(System).in}") InputStream in,
									@Value("#{T(System).out}") PrintStream out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public void print(String str) {
		out.print(str);
	}

	@Override
	public void println(String str) {
		out.println(str);
	}

	@Override
	public String getUserInput(String text) {
		print(text);
		return input();
	}

	@Override
	public Optional<Integer> getUserInputAsInt(String text) {
		print(text);
		try {
			return Optional.of(Integer.parseInt(input()));
		} catch(NumberFormatException e) {
			return Optional.empty();
		}
	}

	private String input() {
		Scanner scanner = new Scanner(in);
		return scanner.nextLine().trim();
	}
}
