package edu.uky.cs.nil.sabre.tvg;

import java.util.ArrayList;
import java.util.List;

import edu.uky.cs.nil.sabre.bench.Benchmark;

public class Main {
	/**
	 * Returns a list of all the {@link Benchmark benchmark problems} to test.
	 * 
	 * @return a list of benchmark problems
	 */
	private static final List<Benchmark> getProblems() {
		ArrayList<Benchmark> list = new ArrayList<>();
		/*						Name				File				Goal	ATL		CTL		EL  */
		list.add(new Benchmark("bribery", 			"bribery",			1,		5,		5,		2	));
		list.add(new Benchmark("deerhunter",	   "deerhunter",		1,		10,		6,		1	));
		list.add(new Benchmark("secretagent",		"secretagent",		1,		8,		8,		1	));
		list.add(new Benchmark("aladdin",	    	"aladdin",			1,		13,		10,		2	));
		list.add(new Benchmark("hospital",	    	"hospital",			1,		11,		5,		3	));
		list.add(new Benchmark("basketball",    	"basketball",		1,		7,		5,		2	));
		list.add(new Benchmark("western",			"western",			1,		8,		5,		1	));
		list.add(new Benchmark("fantasy",		    "fantasy",			1,		9,		3,		2	));
		list.add(new Benchmark("space",	    		"space",			1,		9,		3,		1	));
		list.add(new Benchmark("raiders",			"raiders",			1,		7,		4,		1	));
		list.add(new Benchmark("treasure",			"treasure",			1,		4,		4,		3	));
		list.add(new Benchmark("gramma",	    	"gramma",			1,		6,		5,		2	));
		list.add(new Benchmark("jailbreak",     	"jailbreak",		1,		7,		6,		1	));
		list.add(new Benchmark("lovers",			"lovers",			1,		5,		5,		2	));
		return list;
	}
	
	/**
	 * Runs tree-based and graph-based search on every benchmark problem.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		List<Benchmark> problems = getProblems();
		for(Benchmark problem : problems) {
			TreeTest t = new TreeTest();
			GraphTest g = new GraphTest();
			
			System.out.println("TREE TEST on " + problem.name);
			t.filePath = problem.name;
			t.epi = problem.el;
			t.minutes = 1;
			t.callTreeTest();
			
			System.out.println("GRAPH TEST on " + problem.name);
			g.filePath = problem.name;
			g.epi = problem.el;
			g.minutes = 1;
			g.callGraphTest();
		}
	}
}