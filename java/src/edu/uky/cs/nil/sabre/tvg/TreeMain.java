package edu.uky.cs.nil.sabre.tvg;

public class TreeMain {
	/**
	 * Runs tree-based search on a benchmark problem based on the parameters
	 * 
	 * @param args name of the problem, then the ATL, CTL, and EL respectively
	 */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Usage: java TreeMain <problem> <ATL> <CTL> <EL>");
            System.exit(1);
        }

        String problem = args[0];
        int atl = Integer.parseInt(args[1]);
        int ctl = Integer.parseInt(args[2]);
        int el = Integer.parseInt(args[3]);

        TreeTest t = new TreeTest();
        t.filePath = problem;
        t.epi = el;
        t.minutes = 1;
        System.out.println("Running TREE TEST on " + problem);
        t.callTreeTest();
    }
}