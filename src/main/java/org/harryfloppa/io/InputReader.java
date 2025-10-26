package org.harryfloppa.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.harryfloppa.model.Edge;
import org.harryfloppa.model.Graph;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading graph data from JSON input files.
 */
public class InputReader {
    
    private final Gson gson;

    public InputReader() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    //Reads all graphs from a JSON file.
    public List<Graph> readGraphs(String filePath) throws IOException {
        List<Graph> graphs = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JsonObject rootObject = gson.fromJson(reader, JsonObject.class);
            JsonArray graphsArray = rootObject.getAsJsonArray("graphs");

            for (int i = 0; i < graphsArray.size(); i++) {
                JsonObject graphObj = graphsArray.get(i).getAsJsonObject();
                graphs.add(parseGraph(graphObj));
            }
        }

        return graphs;
    }

    //Parses a single graph from JSON object.
    private Graph parseGraph(JsonObject graphObj) {
        int id = graphObj.get("id").getAsInt();

        // Parse nodes
        JsonArray nodesArray = graphObj.getAsJsonArray("nodes");
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < nodesArray.size(); i++) {
            nodes.add(nodesArray.get(i).getAsString());
        }

        // Parse edges
        JsonArray edgesArray = graphObj.getAsJsonArray("edges");
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < edgesArray.size(); i++) {
            JsonObject edgeObj = edgesArray.get(i).getAsJsonObject();
            String from = edgeObj.get("from").getAsString();
            String to = edgeObj.get("to").getAsString();
            int weight = edgeObj.get("weight").getAsInt();
            edges.add(new Edge(from, to, weight));
        }

        return new Graph(id, nodes, edges);
    }
}
