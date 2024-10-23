package edu.uky.cs.nil.sabre.tvg;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Deque;

import com.opencsv.CSVWriter;

import edu.uky.cs.nil.sabre.Action;
import edu.uky.cs.nil.sabre.Character;
import edu.uky.cs.nil.sabre.Problem;
import edu.uky.cs.nil.sabre.Session;
import edu.uky.cs.nil.sabre.bench.Benchmark;
import edu.uky.cs.nil.sabre.comp.CompiledProblem;
import edu.uky.cs.nil.sabre.graph.StateGraph;
import edu.uky.cs.nil.sabre.graph.StateNode;
import edu.uky.cs.nil.sabre.logic.True;

/**
 * Runs graph-based search on a suite of {@link Benchmark benchmark problems} 
 * up to a defined depth and time, and outputs the results 
 * in a CSV file for each problem
 */
public class GraphTest {
	
	// Static variable for the current StateNode
	private static StateNode n = null;
	
	// Tracks the current depth of the search
	static int depth = 0;
	
	// Counter for the number of visited nodes
	static int visited = 0;
	
	// The current depth limit for the search
	static int limit = 0;
	
	// Maximum depth limit allowed
	static int maxlimit = 50;
	
	// Variables to store the start and end times of the search
	static double end = 0;
	static double start = 0;
	
	// HashMap to track visited nodes and their corresponding depths
	static HashMap<StateNode, Integer> map = new HashMap<>();
	
	// Queue (deque) to manage nodes for breadth-first search
	static Deque<StateNode> q = new ArrayDeque<>();
	
	// Define directories for input problems and output results
	private static final File directory = new File("java/sabre-benchmarks/problems/");
	private static final File directory1 = new File("output/");
	
	// File path for problem files
	static String filePath = "";
	
	// Epistemic limit
	static int epi = 0;
	
	// Time limit for the program in minutes
	static int minutes = 0;
	
	/**
     * This method executes the GraphTest, performing search on a problem using 
     * a graph-based search algorithm and logs the results (depth limit, nodes visited, 
     * and time taken) into a CSV file for analysis.
     */
	public void callGraphTest() {
		try {
			// Initialize a new session
			Session session = new Session();
			
			// Load the problem file from the specified directory
			File file = new File(directory.getPath() + File.separator + filePath + ".txt");
			session.setProblem(file);
			
			print("Compiled Problem", session.getCompiledProblem());
			
			// Create a CSV file for output to store search results
			File file1 = new File(directory1.getPath() + File.separator + filePath + "GraphTest.csv"); 
	        FileWriter outputfile = new FileWriter(file1); 
	        CSVWriter writer = new CSVWriter(outputfile);
	        
	        // Write CSV header
	        String[] header = { "Depth Limit", "Visited Nodes", "Time Taken (ms)" }; 
	        writer.writeNext(header); 
	        
	        // Get the current time and calculate the ending time depending on how long to run each problem for a certain depth
			double startProgram = System.currentTimeMillis();
			double endProgram =  startProgram + minutes * 60 * 1000;
			
			// Loop through depth limits, incrementing until the maximum depth or time limit is reached
			for(limit = 1; limit <= maxlimit && System.currentTimeMillis() <= endProgram; limit++) {
				// Reset counters and timing variables for each depth
				visited = 0;
				end = 0;
				start = 0;
				
				// Retrieve the compiled problem and initialize the state graph
				CompiledProblem g = (CompiledProblem) session.getCompiledProblem();
				StateGraph graph = new StateGraph(g);
				
				// Record the start time of the current search iteration
				start = System.currentTimeMillis();
				
				// Visit the root node of the graph and process nodes in the queue (breadth-first search)
				visit(graph.root, 0);
				while (q.size() > 0) {
					n = q.poll();
					depth = map.get(n);
					if(depth < limit) {
						for(Action a : g.actions)
							// Check if the action's precondition is satisfied for the current node
							if(a.precondition.evaluate(n).equals(True.TRUE)) {
								visit(n.getAfter(a), depth + 1); // Visit the node resulting from applying the action			 
							}
					}  
				}
				
				// Record the end time of the current search iteration
				end = System.currentTimeMillis();
				
				// Print and log the results for the current depth
				System.out.println("Depth Limit " + limit);
				System.out.println("Visited Nodes: " + visited);
				System.out.println("Time Taken " + (end - start) + "ms");
				
				// Write the results to the CSV file
				String [] data = { String.valueOf(limit), String.valueOf(visited), String.valueOf(end-start) }; 
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
	
	// Method to visit a node and add it to the queue if it hasn't been visited
	static void visit(StateNode n, int a) {
		if(!map.containsKey(n)) {
			map.put(n, a);
			q.add(n);
			visited++;
			// Recursively visit the beliefs associated with each character in the node
			for(Character i : n.graph.characters) {
				visit(n.getBeliefs(i), a);
			}
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
