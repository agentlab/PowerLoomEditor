package org.pl.stellaj;


public class Stellaj {
	
	public enum STAGES {
		BEGIN,//0
		KERNEL_STARTUP,//2
		QUOTED_CONSTRAINTS,//3
		GLOBAL_VARS,//4
		CLASS_OBJS,//5
		CLASS_FINALIZING,//6
		METOD_OBJS,//7
		FINALIZING_METHODS,//8
		OTHER//9
	}
	
	private static Stellaj stellaj = new Stellaj();
	
	public static Stellaj getInstance() {
		return stellaj;
	}
	
	public void addInitializer(STAGES stage, int priority, InitHandler h) {
		
	}
}
