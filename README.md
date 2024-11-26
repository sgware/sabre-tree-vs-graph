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
```
