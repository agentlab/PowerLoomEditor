package ru.agentlab.oomph.launcher;

public class PrintServiceImpl implements PrintService {
	@Override
	public void print(String message) {
		System.err.println(message);
	}
}
