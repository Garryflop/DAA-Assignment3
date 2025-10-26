package org.harryfloppa.algorithm;

import org.harryfloppa.model.Edge;
import org.harryfloppa.model.Graph;
import org.harryfloppa.model.MSTResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comparative tests between Prim's and Kruskal's algorithms.
 * Ensures both algorithms produce MSTs with the same total cost.
 */
@DisplayName("Algorithm Comparison Tests")
class AlgorithmComparisonTest {

    private final PrimAlgorithm primAlgorithm = new PrimAlgorithm();
    private final KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm();

    static Stream<Graph> provideTestGraphs() {
        return Stream.of(
                // Simple triangle
                new Graph(1, Arrays.asList("A", "B", "C"),
                        Arrays.asList(
                                new Edge("A", "B", 1),
                                new Edge("B", "C", 2),
                                new Edge("A", "C", 3)
                        )),
                // Assignment example
                new Graph(2, Arrays.asList("A", "B", "C", "D", "E"),
                        Arrays.asList(
                                new Edge("A", "B", 4),
                                new Edge("A", "C", 3),
                                new Edge("B", "C", 2),
                                new Edge("B", "D", 5),
                                new Edge("C", "D", 7),
                                new Edge("C", "E", 8),
                                new Edge("D", "E", 6)
                        )),
                // Complete K4
                new Graph(3, Arrays.asList("A", "B", "C", "D"),
                        Arrays.asList(
                                new Edge("A", "B", 1),
                                new Edge("A", "C", 4),
                                new Edge("A", "D", 3),
                                new Edge("B", "C", 2),
                                new Edge("B", "D", 5),
                                new Edge("C", "D", 6)
                        )),
                // Dense graph
                new Graph(4, Arrays.asList("A", "B", "C", "D", "E", "F"),
                        Arrays.asList(
                                new Edge("A", "B", 2),
                                new Edge("A", "C", 3),
                                new Edge("B", "C", 1),
                                new Edge("B", "D", 5),
                                new Edge("C", "D", 4),
                                new Edge("C", "E", 6),
                                new Edge("D", "E", 2),
                                new Edge("D", "F", 3),
                                new Edge("E", "F", 1)
                        ))
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestGraphs")
    @DisplayName("Both algorithms produce same MST cost")
    void testAlgorithmsProduceSameCost(Graph graph) {
        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        assertEquals(primResult.getTotalCost(), kruskalResult.getTotalCost(),
                String.format("MST costs differ for graph %d", graph.getId()));
        assertEquals(primResult.getMstEdges().size(), kruskalResult.getMstEdges().size(),
                String.format("MST edge counts differ for graph %d", graph.getId()));
    }

    @Test
    @DisplayName("Compare performance on medium graph")
    void testPerformanceComparison() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("A", "C", 4),
                new Edge("B", "C", 2),
                new Edge("B", "D", 6),
                new Edge("C", "D", 3),
                new Edge("C", "E", 7),
                new Edge("D", "E", 5),
                new Edge("D", "F", 8),
                new Edge("E", "F", 4),
                new Edge("E", "G", 9),
                new Edge("F", "G", 6),
                new Edge("F", "H", 10),
                new Edge("G", "H", 7)
        );
        Graph graph = new Graph(1, nodes, edges);

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        // Verify same cost
        assertEquals(primResult.getTotalCost(), kruskalResult.getTotalCost());

        // Both should have V-1 edges
        assertEquals(7, primResult.getMstEdges().size());
        assertEquals(7, kruskalResult.getMstEdges().size());

        // Both should track operations
        assertTrue(primResult.getOperationsCount() > 0);
        assertTrue(kruskalResult.getOperationsCount() > 0);

        // Both should measure execution time
        assertTrue(primResult.getExecutionTimeMs() >= 0);
        assertTrue(kruskalResult.getExecutionTimeMs() >= 0);
    }

    @Test
    @DisplayName("Verify MST has V-1 edges")
    void testMSTEdgeCount() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("B", "C", 2),
                new Edge("C", "D", 3),
                new Edge("D", "E", 4),
                new Edge("A", "E", 10)
        );
        Graph graph = new Graph(1, nodes, edges);

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        assertEquals(4, primResult.getMstEdges().size());
        assertEquals(4, kruskalResult.getMstEdges().size());
    }
}
