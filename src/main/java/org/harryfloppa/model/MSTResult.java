package org.harryfloppa.model;

import java.util.List;

/**
 * Contains the results of running an MST algorithm.
 * Includes the MST edges, total cost, and performance metrics.
 */
public class MSTResult {
    private final List<Edge> mstEdges;
    private final int totalCost;
    private final long operationsCount;
    private final double executionTimeMs;

    public MSTResult(List<Edge> mstEdges, int totalCost, long operationsCount, double executionTimeMs) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.executionTimeMs = executionTimeMs;
    }

    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public long getOperationsCount() {
        return operationsCount;
    }

    public double getExecutionTimeMs() {
        return executionTimeMs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("MST Total Cost: %d%n", totalCost));
        sb.append(String.format("Operations Count: %d%n", operationsCount));
        sb.append(String.format("Execution Time: %.2f ms%n", executionTimeMs));
        sb.append("MST Edges:\n");
        for (Edge edge : mstEdges) {
            sb.append(String.format("  %s%n", edge));
        }
        return sb.toString();
    }
}
