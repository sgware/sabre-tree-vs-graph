package edu.uky.cs.nil.sabre.tvg;

public class GraphMain {
	/**
	 * Runs graph-based search on a benchmark problem based on the parameters
	 * 
	 * @param args name of the problem, then the ATL and CTL respectively
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Usage: java GraphMain <problem> <ATL> <CTL>");
			System.exit(1);
		}
		String problem = args[0];
		int atl = Integer.parseInt(args[1]);
		int ctl = Integer.parseInt(args[2]);
		
		GraphTest g = new GraphTest();
		g.filePath = problem;
		g.minutes = 1;
		System.out.println("Running GRAPH TEST on " + problem);
		g.callGraphTest();
	}
}
