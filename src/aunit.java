
/* aunit.java - AUnit
//
// Copyright (C) 1996-2020 by Michel de Champlain
//
*/

import java.io.*;
import java.util.*;

class Test {
	Test(int number, String name) {
		this.number = number;
		this.name = name;
	}

	int number;
	String name;
}

public class aunit {
	@SuppressWarnings("rawtypes")
	static Vector failedTests = new Vector();

	@SuppressWarnings({ "unchecked", "resource", "rawtypes" })
	public static void main(String[] args) throws IOException {
		String line;

		if (args.length != 1) {
			System.out.println("Usage: aunit Tests.txt");
			return;
		}

		BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));

		int testNumber = 1;

		// Reading all tests
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("Test")) {
				Test test = new Test(testNumber, line);
				String expectedOutput = reader.readLine();
				String output = reader.readLine();
				char status = expectedOutput.equals(output) ? '.' : 'F';
				if (status == 'F') {
					failedTests.addElement(test);
				}
				System.out.print(status);
			} else {
				System.out.println("\nTest #" + testNumber
						+ " should start with \"Test...\" (see file 'runTests.txt' for details)");
				return;
			}
			++testNumber;
		}
		System.out.println();

		// Reporting only if at least one test failed
		if (failedTests.isEmpty()) {
			System.out.println("OK");
		} else {
			System.out.println("\nFailed Tests:");
			for (Enumeration e = failedTests.elements(); e.hasMoreElements();) {
				Test t = (Test) e.nextElement();
				System.out.println("- " + t.name);
			}
		}
	}
}
