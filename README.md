# sabre-tree-vs-graph
Comparing the performance of the Sabre narrative planner on a suite of benchmark
problems using tree-based and graph-based representations of the search space.

## Usage

To clone this project (including Sabre as a submodule), compile the code, and
run it:

```
git clone --recurse-submodules https://github.com/sgware/sabre-tree-vs-graph
cd sabre-tree-vs-graph

javac -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar -sourcepath java/sabre-benchmarks/src;java/src -d java/bin java/src/edu/uky/cs/nil/sabre/tvg/TreeMain.java
javac -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar -sourcepath java/sabre-benchmarks/src;java/src -d java/bin java/src/edu/uky/cs/nil/sabre/tvg/GraphMain.java

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain bribery 5 5 2
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain bribery 5 5

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain deerhunter 10 6 1
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain deerhunter 10 6

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain secretagent 8 8 1
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain secretagent 8 8

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain aladdin 13 10 2
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain aladdin 13 10

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain hospital 11 5 3
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain hospital 11 5

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain basketball 7 5 2
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain basketball 7 5

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain western 8 5 1
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain western 8 5

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain fantasy 9 3 2
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain fantasy 9 3

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain space 9 3 1
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain space 9 3

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain raiders 7 4 1
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain raiders 7 4

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain treasure 4 4 3
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain treasure 4 4

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain gramma 6 5 2
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain gramma 6 5

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain jailbreak 7 6 1
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain jailbreak 7 6

java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.TreeMain lovers 5 5 2
java -Xms500g -Xmx500g -cp java/opencsv/opencsv-5.9.jar;java/sabre/lib/sabre.jar;java/sabre-benchmarks/bin;java/bin edu.uky.cs.nil.sabre.tvg.GraphMain lovers 5 5

```
