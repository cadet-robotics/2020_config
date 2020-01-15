package frc6868.test;

import java.io.IOException;

import frc6868.config.api.Config;

/**
 * A place to test the API
 * 
 * @author Alex Pickering
 */
public class APITester {
	
	public static void main(String[] args) throws IOException {
		Config tcf = new Config("test files/test.json").readFile();
		tcf.setEntry("beans", "beans", "beans", "beans");
		System.out.println(tcf);
		tcf.writeFile("beans.json", true);
		
		System.out.println(tcf.separateCategory("beans"));
	}
}
