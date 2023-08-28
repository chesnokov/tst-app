package com.rntgroup.tstapp.beans;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
	public static final String TESTS_DIR = "tstdir";
	private String path;
	private Properties properties;

	public Configuration(String path) {
		this.path = path;
	}

	public Properties getProperties() throws IOException {
		if(properties == null) {
			Properties p=new Properties();
			p.load(new FileReader(path));
			properties = p;
		}
		return properties;
	}
}
