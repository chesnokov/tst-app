package com.rntgroup.tstapp;

import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.tstapp.beans.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");

		try {
			Application application = ac.getBean("application", Application.class);
			application.run();
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}
}
