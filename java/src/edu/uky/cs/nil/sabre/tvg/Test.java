package edu.uky.cs.nil.sabre.tvg;
import java.io.File;

public class Test {
	// Tracks the current depth of the search
	static int depth = 0;
	
	// Counter for the number of visited nodes
	static int visited = 0;
	
	// The current depth limit for the search
	static int limit = 1;
	
	// Variables to store the start and end times of the search
	static double end = 0;
	static double start = 0;
		
	// Define directories for input problems and output results
	static final File directory = new File("java/sabre-benchmarks/problems/");
	static final File directory1 = new File("output/");
	
	// File path for problem files
	static String filePath = "";
	
	// Epistemic limit
	static int epi = 0;
	
	// Time limit for the program in minutes
	static int minutes = 0;

	// Flag to exit loop
	static boolean breakLoop = true;

	// Initializing depth limit
	static int depthlimit = 0;
	
	// Start time and the end time limit
	double startProgram = 0;
	long endProgram =  0;

	// For checking if the no of visited nodes is same as previous depth
	static long sameVisited = 0;
}
