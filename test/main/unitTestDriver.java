package main;
import org.junit.* ;

public class unitTestDriver {
	
	 public static void main(String[] args){
		 String[] testCases = new String[] {
				 "shared.model.PlayerTest",
				 "shared.model.BoardTest"
		 };
		 org.junit.runner.JUnitCore.main(testCases);
	 }
}
