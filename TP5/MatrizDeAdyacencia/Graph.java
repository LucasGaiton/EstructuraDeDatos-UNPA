package TP5.MatrizDeAdyacencia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
    private Vertex[] vertices;
    private int vertexPosition;
    private boolean[][] edges;
    private int[][] costs;
    private int vertexQuantity;
    private static final int INFINITO = Integer.MAX_VALUE;

    public Graph(int quantity) {
        vertexQuantity = quantity;
        vertices = new Vertex[vertexQuantity];
        vertexPosition = 0;
        edges = new boolean[vertexQuantity][vertexQuantity];
        costs = new int[vertexQuantity][vertexQuantity];

        for (int i = 0; i < vertexQuantity; i++) {
            for (int j = 0; j < vertexQuantity; j++) {
                costs[i][j] = (i == j) ? 0 : INFINITO;
            }
        }
    }

    public void insertVertex(Object element) {
        if (vertexPosition < vertexQuantity) {
            vertices[vertexPosition] = new Vertex();
            vertices[vertexPosition].setElement(element);
            vertexPosition++;
        } else {
            throw new IllegalStateException("Se ha alcanzado el número máximo de vértices.");
        }
    }

    public void insertEdge(Object originElement, Object finishElement, int cost) {
        int originPosition = getVertexOrder(originElement);
        int finishPosition = getVertexOrder(finishElement);
        if (originPosition == -1 || finishPosition == -1) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen.");
        }
        edges[originPosition][finishPosition] = true;
        costs[originPosition][finishPosition] = cost;
    }

    private int getVertexOrder(Object element) {
        for (int i = 0; i < vertexQuantity; i++) {
            if (vertices[i] != null && vertices[i].getElement().equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public void depthFirstSearch(Object element) {
        int startVertex = getVertexOrder(element);
        if (startVertex == -1) {
            throw new IllegalArgumentException("El vértice no existe.");
        }
        List<Integer> visited = new ArrayList<>();
        depthFirst(startVertex, visited);
    }

    private void depthFirst(int element, List<Integer> visited) {
        System.out.print(vertices[element].getElement() + " ");
        visited.add(element);

        for (int adj : adjacents(element)) {
            if (!visited.contains(adj)) {
                depthFirst(adj, visited);
            }
        }
    }

    public void breadthFirstSearch(Object element) {
        int startVertex = getVertexOrder(element);
        if (startVertex == -1) {
            throw new IllegalArgumentException("El vértice no existe.");
        }
        breadthFirst(startVertex);
    }

    private void breadthFirst(int element) {
        List<Integer> visited = new ArrayList<>();
        Queue<Integer> explore = new LinkedList<>();

        explore.add(element);
        visited.add(element);

        while (!explore.isEmpty()) {
            int currentVertex = explore.poll();
            System.out.print(vertices[currentVertex].getElement() + " ");

            for (int adj : adjacents(currentVertex)) {
                if (!visited.contains(adj)) {
                    explore.add(adj);
                    visited.add(adj);
                }
            }
        }
    }

    public List<Integer> dijkstraAlgorithm(Object vertex) {
        int startVertex = getVertexOrder(vertex);
        if (startVertex == -1) {
            throw new IllegalArgumentException("El vértice no existe.");
        }
        return dijkstra(startVertex);
    }

    private List<Integer> dijkstra(int vertex) {
        List<Integer> distance = new ArrayList<>();
        List<Integer> toVisit = new ArrayList<>();

        for (int i = 0; i < vertexQuantity; i++) {
            distance.add((i == vertex) ? 0 : INFINITO);
            toVisit.add(i);
        }

        while (!toVisit.isEmpty()) {
            int u = minimum(distance, toVisit);
            toVisit.remove(Integer.valueOf(u));

            int du = distance.get(u);

            if (du != INFINITO) {
                for (int w : adjacents(u)) {
                    if (toVisit.contains(w)) {
                        int cuw = costs[u][w];
                        if (du + cuw < distance.get(w)) {
                            distance.set(w, du + cuw);
                        }
                    }
                }
            }
        }
        return distance;
    }

    private int minimum(List<Integer> distance, List<Integer> toVisit) {
        int minVertex = toVisit.get(0);
        int minValue = distance.get(minVertex);

        for (int vertex : toVisit) {
            if (distance.get(vertex) < minValue) {
                minValue = distance.get(vertex);
                minVertex = vertex;
            }
        }
        return minVertex;
    }

    private List<Integer> adjacents(int element) {
        List<Integer> adjacentVertices = new ArrayList<>();
        for (int i = 0; i < vertexQuantity; i++) {
            if (edges[element][i]) {
                adjacentVertices.add(i);
            }
        }
        return adjacentVertices;
    }

    public int[][] floyd() {
        int[][] floydMatrix = new int[vertexQuantity][vertexQuantity];
        int[][] P = new int[vertexQuantity][vertexQuantity];

        for (int i = 0; i < vertexQuantity; i++) {
            for (int j = 0; j < vertexQuantity; j++) {
                floydMatrix[i][j] = costs[i][j];
                P[i][j] = (i != j && costs[i][j] < INFINITO) ? i : -1;
            }
        }

        for (int k = 0; k < vertexQuantity; k++) {
            for (int i = 0; i < vertexQuantity; i++) {
                for (int j = 0; j < vertexQuantity; j++) {
                    if (floydMatrix[i][k] != INFINITO && floydMatrix[k][j] != INFINITO &&
                        floydMatrix[i][j] > floydMatrix[i][k] + floydMatrix[k][j]) {
                        
                        floydMatrix[i][j] = floydMatrix[i][k] + floydMatrix[k][j];
                        P[i][j] = P[k][j];
                    }
                }
            }
        }

        System.out.println("Matriz de costos de Floyd:");
        mostrarMatriz(floydMatrix);
        System.out.println("Matriz de predecesores P:");
        mostrarMatriz(P);

        return floydMatrix;
    }

    private void mostrarMatriz(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print((matrix[i][j] == INFINITO ? "INF" : matrix[i][j]) + "\t");
            }
            System.out.println();
        }
    }

    // private static class Vertex {
    //     private Object element;

    //     public Object getElement() { 
    //         return element;
    //     }

    //     public void setElement(Object element) {
    //         this.element = element;
    //     }
    // }
}
