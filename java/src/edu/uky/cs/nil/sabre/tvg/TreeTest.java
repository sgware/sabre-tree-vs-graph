package edu.uky.cs.nil.sabre.tvg;

import java.io.File;
import java.io.FileWriter;

import com.opencsv.CSVWriter;

import edu.uky.cs.nil.sabre.Number;
import edu.uky.cs.nil.sabre.Problem;
import edu.uky.cs.nil.sabre.Session;
import edu.uky.cs.nil.sabre.bench.Benchmark;
import edu.uky.cs.nil.sabre.prog.ProgressionCostFactory;
import edu.uky.cs.nil.sabre.prog.ProgressionPlanner;
import edu.uky.cs.nil.sabre.prog.ProgressionSearch;
import edu.uky.cs.nil.sabre.search.Planner;
import edu.uky.cs.nil.sabre.search.Result;
import edu.uky.cs.nil.sabre.util.Worker;

/**
 * Runs tree-based search on a suite of {@link Benchmark benchmark problems} 
 * up to a defined depth and time, and outputs the results 
 * in a CSV file for each problem
 */
public class TreeTest{
	
	// The current depth limit for the search
	static int limit = 0;
	
	// Maximum depth limit allowed
	static int maxlimit = 20;
	
	// Variables to store the start and end times of the search
	static double start = 0;
	static double end = 0;
	
	// File path for problem files
	static String filePath = "";
	
	// Epistemic limit
	static int epi = 0;
	
	// Time limit for the program in minutes	
	static int minutes = 0;
	
	// Define directories for input problems and output results
	private static final File directory = new File("java/sabre-benchmarks/problems/");
	private static final File directory1 = new File("output/");
		
	/**
     * This method executes the TreeTest, performing search on a problem using 
     * a tree-based search algorithm and logs the results (depth limit, nodes visited, 
     * and time taken) into a CSV file for analysis.
     */
	public void callTreeTest() {
		try {
			// Initialize a new session for problem solving
			Session session = new Session();
			
			// Load the problem file based on the filePath
			File file = new File(directory.getPath() + File.separator + filePath + ".txt");
			session.setProblem(file);
			
			// Prepare the output CSV file to store test results
			File file1 = new File(directory1.getPath() + File.separator + filePath + "TreeTest.csv"); 
	        FileWriter outputfile = new FileWriter(file1); 
	        CSVWriter writer = new CSVWriter(outputfile);
	        String[] header = { "Depth Limit", "Visided Nodes", "Time Taken (ms)" }; 
	        writer.writeNext(header); 
	        
			print("Compiled Problem", session.getCompiledProblem());
			
			// Set goal, search, space, and time limits for the planner
			session.setGoal(Number.get(2));
			session.setSearchLimit(Planner.UNLIMITED_NODES);
			session.setSpaceLimit(Planner.UNLIMITED_NODES);
			session.setTimeLimit(Planner.UNLIMITED_TIME);
			
			// Get the current time and calculate the ending time depending on how long to run each problem for a certain depth
			double startProgram = System.currentTimeMillis();
			double endProgram =  startProgram + minutes * 60 * 1000;
			
			// Loop through depth limits, incrementing until the maximum depth or time limit is reached
			for(limit = 1; limit <= maxlimit && System.currentTimeMillis() <= endProgram; limit++) {
				// Reset time variables for each depth
				end = 0;
				start = 0;
				
				// Set the limits
				session.setAuthorTemporalLimit(limit);
				session.setCharacterTemporalLimit(limit);
				session.setEpistemicLimit(epi);
				
				// Record the start time of the current search iteration
				start = System.currentTimeMillis();
				
				if(session.getPlanner() instanceof ProgressionPlanner) {
					ProgressionCostFactory h = ProgressionCostFactory.ZERO;
					session.setHeuristic(h);				
				}
				
				// Execute the search and get the result
				ProgressionSearch search = (ProgressionSearch) session.getSearch();
				Result<?> result = Worker.get(status -> search.get(status));
				
				// Record the end time of the current search iteration
				end = System.currentTimeMillis();
				
				// Print and log the results for the current depth
				System.out.println("Depth Limit " + limit);
				System.out.println("Visited Nodes " + result.visited);
				System.out.println("Generated Nodes " + result.generated);
				System.out.println("Time Taken: " + (end - start) + " ms");
				
				// Write the results to the CSV file
				String [] data = { String.valueOf(limit), String.valueOf(result.visited), String.valueOf(end-start) }; 
				writer.writeNext(data);
				System.out.println("----------------------------------------");
			}
			writer.close();
		}
		catch(Throwable t) {
			if(t instanceof RuntimeException && t.getCause() != null)
				t = t.getCause();
			System.err.println("Error: " + t.getMessage());
		}
	}
	
	/**
	 * Prints summary statistics of a {@link Problem problem} to standard
	 * output if running in verbose mode.
	 * 
	 * @param key the type of problem
	 * @param problem the problem
	 */
	private static final void print(String key, Problem problem) {
		System.out.println(key + ": " + problem.name);
		System.out.println("  characters: " + problem.universe.characters.size());
		System.out.println("  entities:   " + problem.universe.entities.size());
		System.out.println("  fluents:    " + problem.fluents.size());
		System.out.println("  actions:    " + problem.actions.size());
		System.out.println("  triggers:   " + problem.triggers.size());
	}			
}

