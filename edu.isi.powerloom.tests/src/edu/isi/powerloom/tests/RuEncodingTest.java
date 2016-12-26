package edu.isi.powerloom.tests;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.powerloom.service.PowerLoomService;

import edu.isi.powerloom.PLI;
import edu.isi.powerloom.PlIterator;
import edu.isi.powerloom.logic.LogicObject;
import edu.isi.stella.Module;

public class RuEncodingTest {

	public static final String CONCEPT_NAME = "Concept";
//	public static final String MODULE_NAME_KERNEL = "PL-KERNEL";
	public static final String MODULE_NAME_TEST = "TEST";

	@Test
	public void test() {
		PowerLoomService pls = PowerLoomService.getInstance();

		try {
			pls.loadPlm("RuEncodingTest.plm");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		//Module moduleKernel = PLI.getModule(MODULE_NAME_KERNEL, null);
		Module moduleTest = PLI.getModule(MODULE_NAME_TEST, null);

		//String query = "all ?x (Concept ?x)";
		String query = "all (and (UseCase ?x) (hasTitle ?x ?y))";
		PlIterator answer = PLI.sRetrieve(query, MODULE_NAME_TEST, null);
	    System.out.println();
	    System.out.println("Answers to query `" + query + "'");
	    while (answer.nextP()) {
	      System.out.println(answer.value/* + " " + ((Cons)answer.value).rest.value*/);
	    }

	    System.out.println();

	    LogicObject concept = PLI.getConcept(CONCEPT_NAME, moduleTest, null);
		PlIterator iter = PLI.getConceptInstances(concept, moduleTest, null);
		while (iter.nextP()) {
			String s = PLI.getNthString(iter, 0, null, null);
			int index = s.lastIndexOf("/");
			String trim = s.substring(index + 1);
			if (trim.contains("|")) {
				trim = trim.replaceAll("\\|", "");
			}
			LogicObject sd = PLI.getConcept(trim, moduleTest, null);

			System.out.println(trim + " " + moduleTest + " " + sd);
//			if (PLI.getHomeModule(sd).equals(module)){
				//l.add(trim);
//				editorPane.paletteViewer.addElement(trim);
//			}
		}
	}

}
