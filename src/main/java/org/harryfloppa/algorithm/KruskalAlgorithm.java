package org.harryfloppa.algorithm;

import org.harryfloppa.model.Edge;
import org.harryfloppa.model.Graph;
import org.harryfloppa.model.MSTResult;

import java.util.*;

/**
 * Implementation of Kruskal's algorithm for finding Minimum Spanning Tree.
 * Uses Union-Find (Disjoint Set Union) data structure for efficient cycle detection.
 */
public class KruskalAlgorithm {
    private long operationsCount;
    /**
     * Finds the Minimum Spanning Tree using Kruskal's algorithm.
     * If the graph is disconnected, finds a Minimum Spanning Forest (MSF).
     */
    public MSTResult findMST(Graph graph) {
        operationsCount = 0;
        long startTime = System.nanoTime();

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        // Handle empty graph
        if (graph.getVertexCount() == 0) {
            long endTime = System.nanoTime();
            double executionTimeMs = (endTime - startTime) / 1_000_000.0;
            return new MSTResult(mstEdges, totalCost, operationsCount, executionTimeMs);
        }

        // Sort edges by weight
        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        sortedEdges.sort(Comparator.comparingInt(Edge::getWeight));
        operationsCount += (long) (sortedEdges.size() * Math.log(sortedEdges.size())); // Sorting operations

        // Initialize Union-Find structure
        UnionFind unionFind = new UnionFind(graph.getNodes());

        // Process edges in ascending order of weight
        for (Edge edge : sortedEdges) {
            operationsCount++; // Iteration

            String from = edge.getFrom();
            String to = edge.getTo();

            // Check if adding this edge creates a cycle
            if (!unionFind.connected(from, to)) {
                operationsCount++; // Connected check
                
                // Add edge to MST
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                unionFind.union(from, to);
                operationsCount += 2; // Add edge and union operation

                // Early termination: MST has V-1 edges
                if (mstEdges.size() == graph.getVertexCount() - 1) {
                    operationsCount++; // Size check
                    break;
                }
            }
        }

        // Add Union-Find operations to total count
        operationsCount += unionFind.getOperationsCount();

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        return new MSTResult(mstEdges, totalCost, operationsCount, executionTimeMs);
    }

    /**
     * Union-Find (Disjoint Set Union) data structure with path compression and union by rank.
     * Provides near-constant time operations for union and find.
     */
    private static class UnionFind {
        private final Map<String, String> parent;
        private final Map<String, Integer> rank;
        private long operationsCount;

        public UnionFind(List<String> nodes) {
            parent = new HashMap<>();
            rank = new HashMap<>();
            operationsCount = 0;

            for (String node : nodes) {
                parent.put(node, node);
                rank.put(node, 0);
                operationsCount += 2; // Initialization operations
            }
        }

        //Finds the root of the set containing the given node with path compression.
        public String find(String node) {
            operationsCount++; // Find operation
            
            if (!parent.get(node).equals(node)) {
                operationsCount++; // Comparison
                // Path compression: make every node point directly to root
                parent.put(node, find(parent.get(node)));
                operationsCount++; // Path compression update
            }
            return parent.get(node);
        }

        //Unites the sets containing two nodes using union by rank.
        public void union(String node1, String node2) {
            String root1 = find(node1);
            String root2 = find(node2);
            operationsCount += 2; // Two find operations

            if (root1.equals(root2)) {
                operationsCount++; // Comparison
                return;
            }

            // Union by rank: attach smaller tree under root of larger tree
            int rank1 = rank.get(root1);
            int rank2 = rank.get(root2);
            operationsCount += 2; // Rank retrievals

            if (rank1 < rank2) {
                parent.put(root1, root2);
                operationsCount++; // Parent update
            } else if (rank1 > rank2) {
                parent.put(root2, root1);
                operationsCount++; // Parent update
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank1 + 1);
                operationsCount += 2; // Parent and rank update
            }
        }

        //Checks if two nodes are in the same set.
        public boolean connected(String node1, String node2) {
            return find(node1).equals(find(node2));
        }

        public long getOperationsCount() {
            return operationsCount;
        }
    }
}
