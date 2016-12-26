package org.powerloom.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.powerloom.PushbackBufferedReader;

import edu.isi.powerloom.PLI;

public class PowerLoomService {
	private static PowerLoomService powerLoomService = new PowerLoomService();

	public PowerLoomService() {
		PLI.initialize();
	}


	public static PowerLoomService getInstance() {
		return powerLoomService;
	}

	public static void reset() {
		PLI.resetPowerloom();
	}

	public void loadPlm(String path) throws FileNotFoundException, UnsupportedEncodingException {
		InputStream inputStream = new FileInputStream(path);
		PushbackBufferedReader pbbr = new PushbackBufferedReader(inputStream, "UTF-8");
		PLI.loadNativeStream(pbbr, null);
	}
}
