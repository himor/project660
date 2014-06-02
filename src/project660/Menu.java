package project660;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Menu 
{
	final static int COMMAND_GENERATE = 10;
	final static int COMMAND_ANALYZE  = 20;
	final static int COMMAND_FAKE     = 30;
	
	protected static int choice = 0;
	
	/**
	 * Display default menu
	 * 
	 * @throws IOException 
	 */
	protected static void display() throws IOException 
	{
		Set<Integer> menuChoice = new HashSet<Integer> (Arrays.asList(1, 2, 3));
		int n = 0;
		
		while (!menuChoice.contains(n)) {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			System.out.print("\nMenu:\n1. Generate new graph");
			System.out.print("\n2. Analyze graph");
			System.out.print("\n3. Create small sample graph 'test'");
			System.out.print("\n   Your choice?");
			String line = in.readLine();
			
			try {
				n = Integer.parseInt(line);
			} catch (NumberFormatException e) {
				System.out.print("\n   Please type a numeric value!\n");
			}
		}
		
		switch (n) {
			case 1: choice = COMMAND_GENERATE;
			break;
			case 2: choice = COMMAND_ANALYZE;
			break;
			case 3: choice = COMMAND_FAKE;
			break;
			default: choice = COMMAND_GENERATE;
		}
		
	}
	
}
