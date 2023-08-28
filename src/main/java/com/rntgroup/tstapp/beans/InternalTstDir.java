package com.rntgroup.tstapp.beans;

import com.rntgroup.tstapp.tst.file.InternalTstFile;
import com.rntgroup.tstapp.tst.file.TstFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class InternalTstDir {
	private String tstDir;
	private String tstSuffix;

	public InternalTstDir(String tstDir, String tstSuffix) {
		this.tstDir = tstDir;
		this.tstSuffix = tstSuffix;
	}

	public List<TstFile> getTstFiles() throws IOException {
		String jarName = new File(InternalTstDir.class.getResource("/beans.xml").getFile()).getParent().replaceAll("(!|file:\\\\)", "");
		if(jarName.endsWith(".jar")) {
			return getTstFilesFromJar(jarName);
		}
		return getTstFilesFromFileSystem();
	}

	private List<TstFile> getTstFilesFromJar(String jarName) throws IOException {
		List<TstFile> tstFiles = new ArrayList<>();
		try (JarFile jf = new JarFile(jarName)){

			Enumeration<JarEntry> entries = jf.entries();
			while (entries.hasMoreElements()) {
				JarEntry je = entries.nextElement();
				if (je.getName().startsWith(tstDir) &&
					je.getName().endsWith(tstSuffix)) {
					tstFiles.add(new InternalTstFile("/"+je.getName()));
				}
			}
		}
		return tstFiles;
	}

	private List<TstFile> getTstFilesFromFileSystem() {
		File directory = new File(InternalTstDir.class.getResource("/" + tstDir).getFile());
		List<TstFile> tstFiles = new ArrayList<>();
		File[] files = directory.listFiles();
		if(files != null) {
			for (File entry : files ) {
				if (entry.getName().endsWith(".csv")) {
					tstFiles.add(new InternalTstFile("/" + tstDir + "/" + entry.getName()));
				}
			}
		}
		return tstFiles;
	}


}
