package edu.uky.cs.nil.sabre.tvg;

public class TreeMain {
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