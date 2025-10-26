package org.harryfloppa.model;

import java.util.*;

/**
 * Represents an undirected weighted graph for the city transportation network.
 * Vertices represent city districts, edges represent potential roads.
 */
public class Graph {
    private final int id;
    private final List<String> nodes;
    private final List<Edge> edges;
    private final Map<String, List<Edge>> adjacencyList;

    public Graph(int id, List<String> nodes, List<Edge> edges) {
        this.id = id;
        this.nodes = nodes;
        this.edges = edges;
        this.adjacencyList = buildAdjacencyList();
    }

    private Map<String, List<Edge>> buildAdjacencyList() {
        Map<String, List<Edge>> adjList = new HashMap<>();
        for (String node:nodes) {
            adjList.put(node, new ArrayList<>());
        }
        for (Edge edge: edges) {
            adjList.get(edge.getFrom()).add(edge);
            adjList.get(edge.getTo()).add(new Edge(edge.getFrom(), edge.getTo(), edge.getWeight()));
        }
        return adjList;
    }

    public int getId() {
        return id;
    }
    public List<String> getNodes() {
        return new ArrayList<>(nodes);
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    public Map<String, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public int getVertexCount(){
        return nodes.size();
    }

    public int getEdgeCount(){
        return edges.size();
    }
    @Override
    public String toString() {
        return String.format("Graph{id=%d, vertices=%d, edges=%d}", id, nodes.size(), edges.size());
    }


}
