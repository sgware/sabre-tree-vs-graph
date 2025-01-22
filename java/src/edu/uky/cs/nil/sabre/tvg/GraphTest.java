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
 * up to a defined time limit, and outputs the results 
 * into a CSV file for each problem
 */
public class GraphTest extends Test {
		
	// HashMap to track visited nodes and their corresponding depths
	static HashMap<StateNode, Integer> map = new HashMap<>();
	
	// Queue (deque) to manage nodes for breadth-first search
	static Deque<StateNode> q = new ArrayDeque<>();
	private static StateNode n = null;
	
	/**
     	* This method executes the GraphTest, performing search on a problem using 
     	* a graph-based search algorithm and logs the results (depth, nodes visited, 
     	* and time taken) into a CSV file for analysis.
     	*/
	public void callGraphTest() {
		try {
			// Initialize a new session
			Session session = new Session();
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
			
			// Initialize flag variable to break loop at a limit
			breakLoop = true;
			
			// Initialize counter variable to record the depth limits
			limit = 1;
			
			// Loop through depth limits, incrementing until the time limit is reached
			while(breakLoop) {
				// Reset counters and timing variables for each depth
				visited = 0;
				end = 0;
				start = 0;
				
				// Set flag variable to false to terminate loop at next iteration if limit reached
				breakLoop = false;
				
				// Retrieve the compiled problem and initialize the state graph
				CompiledProblem g = (CompiledProblem) session.getCompiledProblem();
				StateGraph graph = new StateGraph(g);
				
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
				
				// Calculate the end time limit
				endProgram =  (long) (start + minutes * 60 * 1000);
				
				// Visit the root node of the graph and process nodes in the queue (breadth-first search)
				visit(graph.root, 0);
				while (q.size() > 0 && System.currentTimeMillis() <= endProgram) {
					// Set flag variable to true to continue loop at next iteration
					breakLoop = true;
					n = q.poll();
					depth = map.get(n);
					if(depth < limit) {
						for(Action a : g.actions) {
							// Check if the action's precondition is satisfied for the current node
							if(a.precondition.evaluate(n).equals(True.TRUE)) {
								try {
									visit(n.getAfter(a).getAfterTriggers(), depth + 1); // Visit the node resulting from applying the action
								}
								catch (StackOverflowError e) {
									// do nothing
								}
							}
							if(System.currentTimeMillis() >= endProgram) {
								breakLoop = false;
								break;
							}
					}
				}
					
				}
				// Record the end time of the current search iteration
				end = System.currentTimeMillis();
				
				// if time limit is not reached
				if(breakLoop) {
					// Print and log the results for the current depth
					System.out.println("Depth Limit " + limit);
					System.out.println("Visited Nodes: " + visited);
					System.out.println("Time Taken " + (end - start) + "ms");
					
					// Write the results to the CSV file
					String [] data = { String.valueOf(limit), String.valueOf(visited), String.valueOf(end-start) }; 
					writer.writeNext(data); 
					writer.flush();
					System.out.println("----------------------------------------");
				}
				
				// if no of visited nodes is the same as previous depth, terminate
				if(sameVisited == visited) {
					breakLoop = false;
				}
				
				// Update the no of visited nodes and the depth limit
			    	sameVisited = visited;
				limit++;
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
