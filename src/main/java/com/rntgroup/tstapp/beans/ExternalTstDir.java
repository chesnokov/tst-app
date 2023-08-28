package com.rntgroup.tstapp.beans;

import com.rntgroup.tstapp.tst.file.ExternalTstFile;
import com.rntgroup.tstapp.tst.file.TstFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.rntgroup.tstapp.beans.Configuration.TESTS_DIR;

public class ExternalTstDir {
	Configuration conf;

	public ExternalTstDir(Configuration conf) {
		this.conf = conf;
	}

	public List<TstFile> getTstFiles() throws IOException {
		File directory = new File(conf.getProperties().getProperty(TESTS_DIR));
		List<TstFile> tstFiles = new ArrayList<>();
		File[] files = directory.listFiles();
		if(files != null) {
			for (File entry : files ) {
				if (entry.getName().endsWith(".csv")) {
					tstFiles.add(new ExternalTstFile(entry));
				}
			}
		}
		return tstFiles;
	}
}
