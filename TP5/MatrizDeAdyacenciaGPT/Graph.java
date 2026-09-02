package MatrizDeAdyacenciaGPT;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
    private List<Vertex> vertices; // Lista de vértices
    private int vertexQuantity;

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public void addVertex(Object value) {
        this.vertices.add(new Vertex(value));
    }

    public void addEdge(Object originValue, Object destinationValue, int weight) {
        int originIndex = getVertexIndex(originValue);
        int destinationIndex = getVertexIndex(destinationValue);

        if (originIndex == -1 || destinationIndex == -1) {
            throw new IllegalArgumentException("One or both vertices not found");
        }

        this.vertices.get(originIndex).addEdge(destinationIndex, weight);
    }

    private int getVertexIndex(Object value) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getValue().equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public void depthFirstSearch(Object startValue) {
        int startIndex = getVertexIndex(startValue);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Start vertex not found");
        }

        boolean[] visited = new boolean[vertices.size()];
        depthFirst(startIndex, visited);
    }

    private void depthFirst(int index, boolean[] visited) {
        visited[index] = true;
        System.out.print(vertices.get(index).getValue() + " ");

        for (Edge edge : vertices.get(index).getEdges()) {
            if (!visited[edge.getDestination()]) {
                depthFirst(edge.getDestination(), visited);
            }
        }
    }

    public void breadthFirstSearch(Object startValue) {
        int startIndex = getVertexIndex(startValue);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Start vertex not found");
        }

        boolean[] visited = new boolean[vertices.size()];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startIndex);
        visited[startIndex] = true;

        while (!queue.isEmpty()) {
            int index = queue.poll();
            System.out.print(vertices.get(index).getValue() + " ");

            for (Edge edge : vertices.get(index).getEdges()) {
                if (!visited[edge.getDestination()]) {
                    visited[edge.getDestination()] = true;
                    queue.add(edge.getDestination());
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertices.size(); i++) {
            sb.append(i).append(": ").append(vertices.get(i)).append("\n");
        }
        return sb.toString();
    }

}
