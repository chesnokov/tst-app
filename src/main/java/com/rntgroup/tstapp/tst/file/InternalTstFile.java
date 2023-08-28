package com.rntgroup.tstapp.tst.file;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.tstapp.beans.InternalTstDir;
import com.rntgroup.tstapp.tst.Tst;

import java.io.IOException;
import java.io.InputStreamReader;

public class InternalTstFile extends TstFile {
	String path;

	public InternalTstFile(String path) {
		this.path = path;
	}

	@Override
	public Tst getTst() throws IOException, CsvValidationException {
		try(CSVReader csvReader = new CSVReader(new InputStreamReader(InternalTstDir.class.getResourceAsStream(path)))) {
			return readCsv(csvReader);
		}
	}

	@Override
	public String getName() {
		return path;
	}

	@Override
	public String toString() {
		return "InternalTstFile{" +
				"path='" + path + '\'' +
				'}';
	}
}
