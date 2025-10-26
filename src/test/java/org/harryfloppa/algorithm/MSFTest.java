package org.harryfloppa.algorithm;

import org.harryfloppa.model.Edge;
import org.harryfloppa.model.Graph;
import org.harryfloppa.model.MSTResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Minimum Spanning Forest (MSF) on disconnected graphs.
 * Verifies that both algorithms handle multiple connected components correctly.
 */
@DisplayName("Minimum Spanning Forest (MSF) Tests")
class MSFTest {

    private final PrimAlgorithm primAlgorithm = new PrimAlgorithm();
    private final KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm();

    @Test
    @DisplayName("Two disconnected components")
    void testTwoDisconnectedComponents() {
        // Component 1: A-B-C (triangle)
        // Component 2: D-E (single edge)
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("B", "C", 2),
                new Edge("A", "C", 3),
                new Edge("D", "E", 5)
        );
        Graph graph = new Graph(1, nodes, edges);

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        // MSF should have 4 edges (2 trees: 2 edges + 1 edge)
        assertEquals(3, primResult.getMstEdges().size(), "Prim MSF should have 3 edges (V-k where k=2 components)");
        assertEquals(3, kruskalResult.getMstEdges().size(), "Kruskal MSF should have 3 edges");
        
        // Total cost should be: 1 + 2 + 5 = 8
        assertEquals(8, primResult.getTotalCost());
        assertEquals(8, kruskalResult.getTotalCost());
        
        // Both algorithms should produce same total cost
        assertEquals(primResult.getTotalCost(), kruskalResult.getTotalCost());
    }

    @Test
    @DisplayName("Three disconnected components")
    void testThreeDisconnectedComponents() {
        // Component 1: A-B (edge weight 10)
        // Component 2: C-D (edge weight 20)
        // Component 3: E-F (edge weight 30)
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E", "F");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 10),
                new Edge("C", "D", 20),
                new Edge("E", "F", 30)
        );
        Graph graph = new Graph(2, nodes, edges);

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        // MSF should have 3 edges (one per component)
        assertEquals(3, primResult.getMstEdges().size());
        assertEquals(3, kruskalResult.getMstEdges().size());
        
        // Total cost: 10 + 20 + 30 = 60
        assertEquals(60, primResult.getTotalCost());
        assertEquals(60, kruskalResult.getTotalCost());
    }

    @Test
    @DisplayName("Disconnected graph with isolated vertex")
    void testIsolatedVertex() {
        // Component 1: A-B-C (connected)
        // Component 2: D (isolated vertex, no edges)
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 5),
                new Edge("B", "C", 3)
        );
        Graph graph = new Graph(3, nodes, edges);

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        // MSF should have 2 edges (only connecting A-B-C)
        assertEquals(2, primResult.getMstEdges().size());
        assertEquals(2, kruskalResult.getMstEdges().size());
        
        // Total cost: 5 + 3 = 8
        assertEquals(8, primResult.getTotalCost());
        assertEquals(8, kruskalResult.getTotalCost());
    }

    @Test
    @DisplayName("Complex disconnected graph")
    void testComplexDisconnectedGraph() {
        // Component 1: A-B-C-D (4 nodes, 5 edges - complete subgraph)
        // Component 2: E-F-G (3 nodes, 3 edges - triangle)
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E", "F", "G");
        List<Edge> edges = Arrays.asList(
                // Component 1
                new Edge("A", "B", 1),
                new Edge("B", "C", 2),
                new Edge("C", "D", 3),
                new Edge("A", "C", 4),
                new Edge("B", "D", 5),
                // Component 2
                new Edge("E", "F", 6),
                new Edge("F", "G", 7),
                new Edge("E", "G", 8)
        );
        Graph graph = new Graph(4, nodes, edges);

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        // MSF should have 5 edges total (3 for component 1 + 2 for component 2)
        assertEquals(5, primResult.getMstEdges().size());
        assertEquals(5, kruskalResult.getMstEdges().size());
        
        // Total cost: (1+2+3) + (6+7) = 6 + 13 = 19
        assertEquals(19, primResult.getTotalCost());
        assertEquals(19, kruskalResult.getTotalCost());
    }

    @Test
    @DisplayName("MSF edge count formula: E_MSF = V - k")
    void testMSFEdgeCountFormula() {
        // Test the formula: MSF has (V - k) edges where k is number of components
        
        // 2 components: 5 vertices, 3 edges expected
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("B", "C", 2),
                new Edge("D", "E", 3)
        );
        Graph graph = new Graph(5, nodes, edges);

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        int V = 5;
        int k = 2; // components
        int expectedEdges = V - k; // 5 - 2 = 3

        assertEquals(expectedEdges, primResult.getMstEdges().size());
        assertEquals(expectedEdges, kruskalResult.getMstEdges().size());
    }

    @Test
    @DisplayName("Verify both algorithms produce same MSF")
    void testBothAlgorithmsProduceSameMSF() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E", "F");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 10),
                new Edge("B", "C", 15),
                new Edge("A", "C", 20),
                new Edge("D", "E", 5),
                new Edge("E", "F", 8)
        );
        Graph graph = new Graph(6, nodes, edges);

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        // Both should find the same number of edges
        assertEquals(primResult.getMstEdges().size(), kruskalResult.getMstEdges().size());
        
        // Both should calculate the same total cost
        assertEquals(primResult.getTotalCost(), kruskalResult.getTotalCost());
        
        // Expected: (10+15) + (5+8) = 38
        assertEquals(38, primResult.getTotalCost());
    }
}
