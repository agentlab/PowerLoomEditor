package edu.isi.powerloom.tests;


import org.junit.Test;
import org.pl.stellaj.InitHandler;
import org.pl.stellaj.Stellaj;

public class StellajTest {

	@Test
	public void test() {
		Stellaj stellaj = Stellaj.getInstance();
		stellaj.addInitializer(Stellaj.STAGES.BEGIN, 0, new InitHandler() {
			@Override
			public void handle() {
				System.out.println("Hello world!!!");
			}
		});
	}
}
