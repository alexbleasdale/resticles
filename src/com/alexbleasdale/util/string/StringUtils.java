package com.alexbleasdale.util.string;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StringUtils {

	public static String getStringFromFilename(String filename,
			String charsetName) throws IOException {
		File f = new File(filename);
		if (f.length() > Runtime.getRuntime().freeMemory()) {
			throw new IOException("Not enough memory to load "
					+ f.getAbsolutePath());
		}
		byte[] data = new byte[(int) f.length()];
		new DataInputStream(new FileInputStream(f)).readFully(data);
		return new String(data, charsetName);
	}

}
