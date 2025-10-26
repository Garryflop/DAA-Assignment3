package org.harryfloppa;

import org.harryfloppa.algorithm.KruskalAlgorithm;
import org.harryfloppa.algorithm.PrimAlgorithm;
import org.harryfloppa.io.InputReader;
import org.harryfloppa.io.OutputWriter;
import org.harryfloppa.model.Graph;
import org.harryfloppa.model.MSTResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class for running MST algorithms on city transportation networks.
 * Reads input from JSON, executes Prim's and Kruskal's algorithms, and outputs results.
 */
public class MyApplication {
    
    private static final String DEFAULT_INPUT_FILE = "src/data/input.json";
    private static final String DEFAULT_OUTPUT_FILE = "src/data/output.json";

    public static void main(String[] args) {
        String inputFile = args.length > 0 ? args[0] : DEFAULT_INPUT_FILE;
        String outputFile = args.length > 1 ? args[1] : DEFAULT_OUTPUT_FILE;

        System.out.println("=".repeat(80));
        System.out.println("City Transportation Network Optimization - MST Analysis");
        System.out.println("=".repeat(80));
        System.out.println();

        try {
            // Read input graphs
            InputReader inputReader = new InputReader();
            List<Graph> graphs = inputReader.readGraphs(inputFile);
            System.out.printf("✓ Successfully loaded %d graph(s) from '%s'%n%n", graphs.size(), inputFile);

            // Process each graph with both algorithms
            Map<Integer, OutputWriter.AlgorithmResults> results = new HashMap<>();
            Map<Integer, Graph> graphMap = new HashMap<>();
            
            PrimAlgorithm primAlgorithm = new PrimAlgorithm();
            KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm();

            for (Graph graph : graphs) {
                graphMap.put(graph.getId(), graph);
                System.out.println("-".repeat(80));
                System.out.printf("Processing Graph #%d: %d vertices, %d edges%n", 
                        graph.getId(), graph.getVertexCount(), graph.getEdgeCount());
                System.out.println("-".repeat(80));

                // Run Prim's algorithm
                System.out.println("\n🔹 Running Prim's Algorithm...");
                MSTResult primResult = primAlgorithm.findMST(graph);
                printAlgorithmResult("Prim", primResult);

                // Run Kruskal's algorithm
                System.out.println("\n🔹 Running Kruskal's Algorithm...");
                MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);
                printAlgorithmResult("Kruskal", kruskalResult);

                // Verify results match
                System.out.println("\n🔹 Verification:");
                if (primResult.getTotalCost() == kruskalResult.getTotalCost()) {
                    System.out.printf("   ✓ MST costs match: %d%n", primResult.getTotalCost());
                } else {
                    System.out.printf("   ✗ WARNING: MST costs differ! Prim: %d, Kruskal: %d%n", 
                            primResult.getTotalCost(), kruskalResult.getTotalCost());
                }

                // Compare performance
                comparePerformance(primResult, kruskalResult);

                results.put(graph.getId(), new OutputWriter.AlgorithmResults(primResult, kruskalResult));
                System.out.println();
            }

            // Write results to output file
            OutputWriter outputWriter = new OutputWriter();
            outputWriter.writeResults(outputFile, results, graphMap);
            System.out.println("=".repeat(80));
            System.out.printf("✓ Results successfully written to '%s'%n", outputFile);
            System.out.println("=".repeat(80));

        } catch (IOException e) {
            System.err.printf("Error: %s%n", e.getMessage());
            System.err.println("\nUsage: java Main [input_file] [output_file]");
            System.err.printf("  Default input:  %s%n", DEFAULT_INPUT_FILE);
            System.err.printf("  Default output: %s%n", DEFAULT_OUTPUT_FILE);
            System.exit(1);
        }
    }

    /**
     * Prints the results of an MST algorithm.
     */
    private static void printAlgorithmResult(String algorithmName, MSTResult result) {
        System.out.printf("   Total Cost: %d%n", result.getTotalCost());
        System.out.printf("   Operations: %,d%n", result.getOperationsCount());
        System.out.printf("   Execution Time: %.3f ms%n", result.getExecutionTimeMs());
        System.out.printf("   MST Edges (%d):%n", result.getMstEdges().size());
        for (int i = 0; i < result.getMstEdges().size(); i++) {
            System.out.printf("      %d. %s%n", i + 1, result.getMstEdges().get(i));
        }
    }

    /**
     * Compares performance metrics between Prim's and Kruskal's algorithms.
     */
    private static void comparePerformance(MSTResult primResult, MSTResult kruskalResult) {
        System.out.println("\n🔹 Performance Comparison:");
        
        // Operations comparison
        long opDiff = primResult.getOperationsCount() - kruskalResult.getOperationsCount();
        double opPercent = Math.abs(opDiff) * 100.0 / Math.min(primResult.getOperationsCount(), 
                kruskalResult.getOperationsCount());
        System.out.printf("   Operations: Prim=%,d, Kruskal=%,d (diff: %+,d, %.1f%%)%n",
                primResult.getOperationsCount(), kruskalResult.getOperationsCount(), opDiff, opPercent);

        // Time comparison
        double timeDiff = primResult.getExecutionTimeMs() - kruskalResult.getExecutionTimeMs();
        double timePercent = Math.abs(timeDiff) * 100.0 / Math.min(primResult.getExecutionTimeMs(), 
                kruskalResult.getExecutionTimeMs());
        System.out.printf("   Time: Prim=%.3f ms, Kruskal=%.3f ms (diff: %+.3f ms, %.1f%%)%n",
                primResult.getExecutionTimeMs(), kruskalResult.getExecutionTimeMs(), timeDiff, timePercent);

        // Winner determination
        String winner = timeDiff < 0 ? "Prim" : (timeDiff > 0 ? "Kruskal" : "Tie");
        System.out.printf("   ⭐ Faster algorithm: %s%n", winner);
    }
}