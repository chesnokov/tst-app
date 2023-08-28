package com.rntgroup.tstapp.tst.file;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rntgroup.tstapp.tst.Tst;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ExternalTstFile extends TstFile {
	private File file;

	public ExternalTstFile(File file) {
		this.file = file;
	}

	@Override
	public Tst getTst() throws IOException, CsvValidationException {
		try(CSVReader csvReader = new CSVReader(new FileReader(file))) {
			return readCsv(csvReader);
		}
	}

	@Override
	public String getName() {
		return file.toString();
	}

	@Override
	public String toString() {
		return "ExternalTstFile{" +
				"file=" + file +
				'}';
	}
}
