package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DijkstraAlgorithm {
    private final List<Edge> edges;
    private List<Vertex> settledNodes;
    private List<Vertex> unSettledNodes;
    private Vertex[] predecessors;
    private Integer[] distance;

    public DijkstraAlgorithm(Graph graph) {
        this.edges = new ArrayList<>(graph.getEdges());
    }

    public List<Vertex> getPath(Vertex source, Vertex target) {
        List<Vertex> path = new ArrayList<>();
        
        this.execute(source);
        Vertex step = target;
        
        if (predecessors[step.getIdF()] == null) {
            return null;
        }
        
        path.add(step);
        
        while (predecessors[step.getIdF()] != null) {
            step = predecessors[step.getIdF()];
            path.add(step);
        }
        
        List<Vertex> pathReversed = new ArrayList<>();
        
        for (int i = 0; i < path.size(); i++) {
        	pathReversed.add(path.get(path.size() - 1 - i));
        }
        
        return pathReversed;
    }

    private void execute(Vertex source) {
        settledNodes = new ArrayList<>();
        unSettledNodes = new ArrayList<>();
        predecessors = new Vertex[50];
        distance = new Integer[50];
        distance[source.getIdF()] = 0;
        unSettledNodes.add(source);
        
        while (unSettledNodes.size() > 0) {
            Vertex node = getMinimum(unSettledNodes);
            if (!settledNodes.contains(node)) settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        
        for (Vertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance[target.getIdF()] = getShortestDistance(node) + getDistance(node, target);
                predecessors[target.getIdF()] = node;
                if (!settledNodes.contains(target)) unSettledNodes.add(target);
            }
        }
    }

    private int getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        
        throw new RuntimeException("Should not happen");
    }

    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<>();
        
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        
        return neighbors;
    }

    private Vertex getMinimum(List<Vertex> vertexes) {
        Vertex minimum = vertexes.size() == 0 ? null : (Vertex) vertexes.toArray()[0];
        
        for (Vertex vertex : vertexes) {
            if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                minimum = vertex;
            }
        }
        
        return minimum;
    }

    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Vertex destination) {
        Integer result = distance[destination.getIdF()];
        return result == null ? Integer.MAX_VALUE : result;
    }
}
