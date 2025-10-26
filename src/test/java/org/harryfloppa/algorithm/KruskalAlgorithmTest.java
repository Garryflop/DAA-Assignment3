package org.harryfloppa.algorithm;

import org.harryfloppa.model.Edge;
import org.harryfloppa.model.Graph;
import org.harryfloppa.model.MSTResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for Kruskal's algorithm implementation.
 */
@DisplayName("Kruskal's Algorithm Tests")
class KruskalAlgorithmTest {

    private final KruskalAlgorithm algorithm = new KruskalAlgorithm();

    @Test
    @DisplayName("Simple triangle graph")
    void testSimpleTriangle() {
        List<String> nodes = Arrays.asList("A", "B", "C");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("B", "C", 2),
                new Edge("A", "C", 3)
        );
        Graph graph = new Graph(1, nodes, edges);

        MSTResult result = algorithm.findMST(graph);

        assertEquals(3, result.getTotalCost());
        assertEquals(2, result.getMstEdges().size());
        assertTrue(result.getOperationsCount() > 0);
        assertTrue(result.getExecutionTimeMs() >= 0);
    }

    @Test
    @DisplayName("Five node graph from assignment template")
    void testAssignmentExample() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 4),
                new Edge("A", "C", 3),
                new Edge("B", "C", 2),
                new Edge("B", "D", 5),
                new Edge("C", "D", 7),
                new Edge("C", "E", 8),
                new Edge("D", "E", 6)
        );
        Graph graph = new Graph(1, nodes, edges);

        MSTResult result = algorithm.findMST(graph);

        assertEquals(16, result.getTotalCost());
        assertEquals(4, result.getMstEdges().size());
    }

    @Test
    @DisplayName("Empty graph")
    void testEmptyGraph() {
        List<String> nodes = Collections.emptyList();
        List<Edge> edges = Collections.emptyList();
        Graph graph = new Graph(1, nodes, edges);

        MSTResult result = algorithm.findMST(graph);

        assertEquals(0, result.getTotalCost());
        assertEquals(0, result.getMstEdges().size());
    }

    @Test
    @DisplayName("Single node graph")
    void testSingleNode() {
        List<String> nodes = Collections.singletonList("A");
        List<Edge> edges = Collections.emptyList();
        Graph graph = new Graph(1, nodes, edges);

        MSTResult result = algorithm.findMST(graph);

        assertEquals(0, result.getTotalCost());
        assertEquals(0, result.getMstEdges().size());
    }

    @Test
    @DisplayName("Two nodes connected")
    void testTwoNodes() {
        List<String> nodes = Arrays.asList("A", "B");
        List<Edge> edges = Collections.singletonList(new Edge("A", "B", 5));
        Graph graph = new Graph(1, nodes, edges);

        MSTResult result = algorithm.findMST(graph);

        assertEquals(5, result.getTotalCost());
        assertEquals(1, result.getMstEdges().size());
    }

    @Test
    @DisplayName("Complete graph K4")
    void testCompleteGraphK4() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("A", "C", 4),
                new Edge("A", "D", 3),
                new Edge("B", "C", 2),
                new Edge("B", "D", 5),
                new Edge("C", "D", 6)
        );
        Graph graph = new Graph(1, nodes, edges);

        MSTResult result = algorithm.findMST(graph);

        assertEquals(6, result.getTotalCost()); // 1 + 2 + 3
        assertEquals(3, result.getMstEdges().size());
    }

    @Test
    @DisplayName("Linear chain graph")
    void testLinearChain() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("B", "C", 2),
                new Edge("C", "D", 3),
                new Edge("D", "E", 4)
        );
        Graph graph = new Graph(1, nodes, edges);

        MSTResult result = algorithm.findMST(graph);

        assertEquals(10, result.getTotalCost()); // 1 + 2 + 3 + 4
        assertEquals(4, result.getMstEdges().size());
    }

    @Test
    @DisplayName("Graph with equal weight edges")
    void testEqualWeights() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 5),
                new Edge("B", "C", 5),
                new Edge("C", "D", 5),
                new Edge("A", "D", 5)
        );
        Graph graph = new Graph(1, nodes, edges);

        MSTResult result = algorithm.findMST(graph);

        assertEquals(15, result.getTotalCost()); // Any 3 edges
        assertEquals(3, result.getMstEdges().size());
    }

    @Test
    @DisplayName("Large weights")
    void testLargeWeights() {
        List<String> nodes = Arrays.asList("A", "B", "C");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1000),
                new Edge("B", "C", 2000),
                new Edge("A", "C", 3000)
        );
        Graph graph = new Graph(1, nodes, edges);

        MSTResult result = algorithm.findMST(graph);

        assertEquals(3000, result.getTotalCost());
        assertEquals(2, result.getMstEdges().size());
    }
}
