package org.powerloom;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class PrintableStringWriter extends OutputStreamWriter {

	public PrintableStringWriter(OutputStream out) {
		super(out);
	}

	public void println() {
		try {
			super.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void println(String str) {
		try {
			super.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		println();
	}

	public void println(Object o) {
		try {
			super.write(o.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		println();
	}

	public void print(String str) {
		try {
			super.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void print(Object o) {
		try {
			super.write(o.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
