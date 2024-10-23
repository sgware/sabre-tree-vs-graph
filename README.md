# sabre-tree-vs-graph
Comparing the performance of the Sabre narrative planner on a suite of benchmark
problems using tree-based and graph-based representations of the search space.

## Usage

To clone this project (including Sabre as a submodule), compile the code, and
run it:

```
git clone --recurse-submodules https://github.com/sgware/sabre-tree-vs-graph
cd sabre-tree-vs-graph/java
javac -cp opencsv/opencsv-5.9.jar;sabre/lib/sabre.jar -sourcepath sabre-benchmarks/src;src -d bin src/edu/uky/cs/nil/sabre/tvg/Main.java
java -Xms10g -Xmx10g -cp opencsv/opencsv-5.9.jar;sabre/lib/sabre.jar;sabre-benchmarks/bin;bin edu.uky.cs.nil.sabre.tvg.Main
```
