package edu.uky.cs.nil.sabre.tvg;

public class GraphMain {
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
        g.epi = ctl; // Assuming CTL is used similarly to EL
        g.minutes = 1;
        System.out.println("Running GRAPH TEST on " + problem);
        g.callGraphTest();
    }
}
