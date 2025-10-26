package org.harryfloppa.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.harryfloppa.model.Edge;
import org.harryfloppa.model.Graph;
import org.harryfloppa.model.MSTResult;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Handles writing algorithm results to JSON output files.
 */
public class OutputWriter {
    
    private final Gson gson;

    public OutputWriter() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    //Writes the results of both algorithms to a JSON file.
    public void writeResults(String filePath, Map<Integer, AlgorithmResults> results, 
                            Map<Integer, Graph> graphs) throws IOException {
        JsonObject rootObject = new JsonObject();
        JsonArray resultsArray = new JsonArray();

        for (Map.Entry<Integer, AlgorithmResults> entry : results.entrySet()) {
            int graphId = entry.getKey();
            AlgorithmResults algResults = entry.getValue();
            Graph graph = graphs.get(graphId);

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("graph_id", graphId);

            // Input stats
            JsonObject inputStats = new JsonObject();
            inputStats.addProperty("vertices", graph.getVertexCount());
            inputStats.addProperty("edges", graph.getEdgeCount());
            resultObj.add("input_stats", inputStats);

            // Prim's results
            resultObj.add("prim", createAlgorithmResultJson(algResults.getPrimResult()));

            // Kruskal's results
            resultObj.add("kruskal", createAlgorithmResultJson(algResults.getKruskalResult()));

            resultsArray.add(resultObj);
        }

        rootObject.add("results", resultsArray);

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(rootObject, writer);
        }
    }

    //Creates JSON object for a single algorithm result.
    private JsonObject createAlgorithmResultJson(MSTResult result) {
        JsonObject resultObj = new JsonObject();

        // MST edges
        JsonArray edgesArray = new JsonArray();
        for (Edge edge : result.getMstEdges()) {
            JsonObject edgeObj = new JsonObject();
            edgeObj.addProperty("from", edge.getFrom());
            edgeObj.addProperty("to", edge.getTo());
            edgeObj.addProperty("weight", edge.getWeight());
            edgesArray.add(edgeObj);
        }
        resultObj.add("mst_edges", edgesArray);

        // Metrics
        resultObj.addProperty("total_cost", result.getTotalCost());
        resultObj.addProperty("operations_count", result.getOperationsCount());
        resultObj.addProperty("execution_time_ms", 
                Math.round(result.getExecutionTimeMs() * 100.0) / 100.0);

        return resultObj;
    }

    //Container class for holding results from both algorithms.
    public static class AlgorithmResults {
        private final MSTResult primResult;
        private final MSTResult kruskalResult;

        public AlgorithmResults(MSTResult primResult, MSTResult kruskalResult) {
            this.primResult = primResult;
            this.kruskalResult = kruskalResult;
        }

        public MSTResult getPrimResult() {
            return primResult;
        }

        public MSTResult getKruskalResult() {
            return kruskalResult;
        }
    }
}
