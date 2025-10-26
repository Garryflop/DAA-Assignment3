package org.harryfloppa.algorithm;

import org.harryfloppa.model.Edge;
import org.harryfloppa.model.Graph;
import org.harryfloppa.model.MSTResult;

import java.util.*;

/**
 * Implementation of Prim's algorithm for finding Minimum Spanning Tree.
 * Uses a priority queue to efficiently select the minimum weight edge at each step.
 */

public class PrimAlgorithm {
    private long operationsCount;
    /**
     * Finds the Minimum Spanning Tree using Prim's algorithm.
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

        Set<String> visited = new HashSet<>();
        Map<String, List<Edge>> adjacencyList = graph.getAdjacencyList();

        // Process each connected component
        for (String startNode : graph.getNodes()) {
            if (!visited.contains(startNode)) {
                operationsCount++; // Component check
                // Run Prim's algorithm from this unvisited node
                primFromNode(startNode, adjacencyList, visited, mstEdges, totalCost);
            }
        }

        // Calculate total cost from collected edges
        totalCost = mstEdges.stream().mapToInt(Edge::getWeight).sum();

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        return new MSTResult(mstEdges, totalCost, operationsCount, executionTimeMs);
    }

    /**
     * Runs Prim's algorithm from a single starting node (processes one component).
     */
    private int primFromNode(String startNode, Map<String, List<Edge>> adjacencyList,
                             Set<String> visited, List<Edge> mstEdges, int totalCost) {
        PriorityQueue<EdgeWithNode> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(e -> e.edge.getWeight())
        );

        visited.add(startNode);
        operationsCount++; // Add to visited set

        // Add all edges from start node to priority queue
        for (Edge edge : adjacencyList.get(startNode)) {
            priorityQueue.offer(new EdgeWithNode(edge, edge.getTo()));
            operationsCount++; // Queue insertion
        }

        // Main loop: continue until priority queue is empty
        while (!priorityQueue.isEmpty()) {
            operationsCount++; // Queue check

            EdgeWithNode current = priorityQueue.poll();
            operationsCount++; // Queue removal

            // Skip if the destination node is already visited
            if (visited.contains(current.node)) {
                operationsCount++; // Visited check
                continue;
            }

            // Add edge to MST
            mstEdges.add(current.edge);
            totalCost += current.edge.getWeight();
            visited.add(current.node);
            operationsCount += 3; // Add edge, update cost, mark visited

            // Add all edges from the newly added node
            for (Edge edge : adjacencyList.get(current.node)) {
                operationsCount++; // Iteration
                if (!visited.contains(edge.getTo())) {
                    operationsCount++; // Visited check
                    priorityQueue.offer(new EdgeWithNode(edge, edge.getTo()));
                    operationsCount++; // Queue insertion
                }
            }
        }

        return totalCost;
    }

    //Helper class to store an edge along with its destination node in the priority queue.
    private static class EdgeWithNode {
        Edge edge;
        String node;

        EdgeWithNode(Edge edge, String node) {
            this.edge = edge;
            this.node = node;
        }
    }
}
