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
 * up to a defined time and depth limit, and outputs the results 
 * into a CSV file for each problem
 */
public class TreeTest extends Test{
	/**
	 * This method executes the TreeTest, performing search on a problem using
	 * a tree-based search algorithm and logs the results (depth, nodes generated, 
	 * nodes visited, and time taken) into a CSV file for analysis.
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
			String[] header = { "Depth Limit", "Generated Nodes", "Visited Nodes", "Time Taken (ms)" };
			writer.writeNext(header); 
	        
			print("Compiled Problem", session.getCompiledProblem());
			
			// Calculate the end time limit
			endProgram =  (minutes * 60 * 1000);
			
			// Set goal, search, space, and time limits for the planner
			session.setGoal(Number.get(1000));
			session.setSearchLimit(Planner.UNLIMITED_NODES);
			session.setSpaceLimit(Planner.UNLIMITED_NODES);
			session.setTimeLimit(endProgram);
			
			// Set flag variable to true
			breakLoop = true;
			
			// Initialize counter variable to record the depth limits
			limit = 1;
            depthlimit = 1;
            
			// Loop through depth limits, incrementing until the time or depth limit is reached
			while (breakLoop) {
				// Reset time variables for each depth
				end = 0;
				start = 0;
				
				// Set the limits
				session.setAuthorTemporalLimit(limit);
				session.setCharacterTemporalLimit(limit);
				session.setEpistemicLimit(epi);			
				
				// Set the heuristic to 0
				if(session.getPlanner() instanceof ProgressionPlanner) {
					ProgressionCostFactory h = ProgressionCostFactory.ZERO;
					session.setHeuristic(h);				
				}
				
				// Run garbage collector and pause briefly.
				System.gc();
				try {
					Thread.sleep(1000);
				}
				catch(InterruptedException e) {
					// do nothing
				}
				
				// Record the start time of the current search iteration
				start = System.currentTimeMillis();
				
				// Execute the search and get the result
				ProgressionSearch search = (ProgressionSearch) session.getSearch();
				Result<?> result = Worker.get(status -> search.get(status));

				end = System.currentTimeMillis();
				
				// Break loop if time limit is reached 
				if((end-start) > endProgram || depthlimit == 101) {
					breakLoop = false;
				}
				
				if (breakLoop) {
					// Print and log the results for the current depth
					System.out.println("Depth Limit " + limit);
					System.out.println("Visited Nodes " + result.visited);
					System.out.println("Generated Nodes " + result.generated);
					System.out.println("Time Taken: " + (end - start) + " ms");
				
					// Write the results to the CSV file
					String [] data = { String.valueOf(limit), String.valueOf(result.generated), String.valueOf(result.visited), String.valueOf(end-start) }; 
					writer.writeNext(data);
					writer.flush();
					System.out.println("----------------------------------------");
				}
				// Break loop if no of visited nodes is the same as previous depth
				if(result.visited == sameVisited) {
					breakLoop = false;
				}
				// Update the no of visited nodes and the depth limit
				sameVisited = result.visited;			
				limit++;
				depthlimit++;
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

