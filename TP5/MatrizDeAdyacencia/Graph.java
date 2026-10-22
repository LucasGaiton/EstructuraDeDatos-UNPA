package TP5.MatrizDeAdyacencia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Grafo dirigido con pesos, representado mediante matriz de adyacencia.
// - edges[i][j] = true  -> existe la arista i -> j
// - costs[i][j]          -> costo (peso) de la arista i -> j (INFINITO si no existe)
// Los vértices se guardan en el arreglo 'vertices' y se identifican por su etiqueta.
public class Graph {
    private Vertex[] vertices;   // Vértices del grafo (etiqueta) indexados por posición
    private int vertexPosition;  // Cantidad de vértices insertados = próximo índice libre
    private boolean[][] edges;   // Matriz de adyacencia: true si existe la arista i -> j
    private int[][] costs;       // Matriz de pesos de las aristas
    private int vertexQuantity;  // Capacidad máxima del grafo (V)
    private static final int INFINITO = Integer.MAX_VALUE; // Centinela: "no hay camino"

    // Constructor: crea el grafo vacío con capacidad para 'quantity' vértices.
    // Deja reservadas las matrices V x V y la diagonal de costos en 0
    // (la distancia de un vértice a sí mismo es 0); el resto queda en INFINITO.
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

    // Inserta un vértice nuevo (identificado por su etiqueta) en la posición libre actual.
    // Si el grafo ya tiene todos sus vértices, lanza IllegalStateException.
    public void insertVertex(Object element) {
        if (vertexPosition < vertexQuantity) {
            // Crea el vértice, le asigna la etiqueta y lo deja en la posición vertexPosition.
            vertices[vertexPosition] = new Vertex();
            vertices[vertexPosition].setElement(element);
            vertexPosition++; // Avanza el puntero al próximo índice libre.
        } else {
            throw new IllegalStateException("Se ha alcanzado el número máximo de vértices.");
        }
    }

    // Inserta la arista dirigida origen -> finishElement con el costo indicado.
    // Las etiquetas se traducen a índices internos con getVertexOrder.
    public void insertEdge(Object originElement, Object finishElement, int cost) {
        int originPosition = getVertexOrder(originElement);
        int finishPosition = getVertexOrder(finishElement);
        if (originPosition == -1 || finishPosition == -1) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen.");
        }
        // Marca la existencia de la arista y guarda su peso en la matriz de costos.
        edges[originPosition][finishPosition] = true;
        costs[originPosition][finishPosition] = cost;
    }

    // Traduce una etiqueta al índice (posición) del vértice dentro del arreglo.
    // Búsqueda lineal O(V): devuelve -1 si la etiqueta no está en el grafo.
    private int getVertexOrder(Object element) {
        for (int i = 0; i < vertexQuantity; i++) {
            if (vertices[i] != null && vertices[i].getElement().equals(element)) {
                return i;
            }
        }
        return -1;
    }

    // Búsqueda en profundidad (DFS) desde el vértice con la etiqueta indicada.
    public void depthFirstSearch(Object element) {
        int startVertex = getVertexOrder(element);
        if (startVertex == -1) {
            throw new IllegalArgumentException("El vértice no existe.");
        }
        List<Integer> visited = new ArrayList<>(); // Vértices ya visitados (contains es lineal).
        depthFirst(startVertex, visited);
    }

    // DFS recursivo: imprime el vértice, lo marca como visitado y sigue por
    // cada vecino que todavía no fue visitado.
    private void depthFirst(int element, List<Integer> visited) {
        System.out.print(vertices[element].getElement() + " ");
        visited.add(element);

        for (int adj : adjacents(element)) {
            if (!visited.contains(adj)) {
                depthFirst(adj, visited);
            }
        }
    }

    // Búsqueda en anchura (BFS) desde el vértice con la etiqueta indicada.
    public void breadthFirstSearch(Object element) {
        int startVertex = getVertexOrder(element);
        if (startVertex == -1) {
            throw new IllegalArgumentException("El vértice no existe.");
        }
        breadthFirst(startVertex);
    }

    // BFS iterativo con una cola: se encola el origen y, mientras la cola tenga
    // elementos, se extrae uno (poll), se imprime y se encolan sus vecinos no visitados.
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

    // Camino más corto (Dijkstra) desde el vértice indicado.
    // Devuelve una lista de distancias donde el índice es la posición del vértice.
    public List<Integer> dijkstraAlgorithm(Object vertex) {
        int startVertex = getVertexOrder(vertex);
        if (startVertex == -1) {
            throw new IllegalArgumentException("El vértice no existe.");
        }
        return dijkstra(startVertex);
    }

    // Implementación de Dijkstra sin heap (O(V^2)): en cada vuelta se elige el
    // vértice no visitado con menor distancia y se relajan sus aristas.
    private List<Integer> dijkstra(int vertex) {
        List<Integer> distance = new ArrayList<>(); // Distancia mínima conocida hasta cada vértice.
        List<Integer> toVisit = new ArrayList<>();    // Vértices que todavía no fueron visitados.

        // El origen arranca en distancia 0; el resto, en INFINITO (aún sin camino).
        for (int i = 0; i < vertexQuantity; i++) {
            distance.add((i == vertex) ? 0 : INFINITO);
            toVisit.add(i);
        }

        while (!toVisit.isEmpty()) {
            // Se toma el no visitado con menor distancia y se marca como visitado.
            int u = minimum(distance, toVisit);
            toVisit.remove(Integer.valueOf(u));

            int du = distance.get(u);

            // Solo se relajan las aristas de los vértices que son alcanzables.
            if (du != INFINITO) {
                for (int w : adjacents(u)) {
                    // Solo se actualizan los vecinos que siguen sin visitar.
                    if (toVisit.contains(w)) {
                        int cuw = costs[u][w];
                        // Si el camino pasando por u es más corto, se actualiza la distancia.
                        if (du + cuw < distance.get(w)) {
                            distance.set(w, du + cuw);
                        }
                    }
                }
            }
        }
        return distance;
    }

    // Devuelve el vértice de toVisit que tiene la menor distancia asociada.
    // Recorrido lineal (O(V)), por eso el algoritmo de Dijkstra queda en O(V^2).
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

    // Devuelve los índices de los vecinos de salida del vértice indicado.
    // Se obtiene recorriendo una fila de la matriz de adyacencia (O(V)).
    private List<Integer> adjacents(int element) {
        List<Integer> adjacentVertices = new ArrayList<>();
        for (int i = 0; i < vertexQuantity; i++) {
            if (edges[element][i]) {
                adjacentVertices.add(i);
            }
        }
        return adjacentVertices;
    }

    // Floyd-Warshall: calcula el camino más corto entre TODOS los pares de vértices.
    // Devuelve la matriz de distancias mínimas e imprime también la de predecesores.
    public int[][] floyd() {
        int[][] floydMatrix = new int[vertexQuantity][vertexQuantity]; // Matriz de distancias mínimas.
        int[][] P = new int[vertexQuantity][vertexQuantity];           // Matriz de predecesores (origen del camino).

        // Se parte de la matriz de costos: P guarda i si hay camino directo i -> j, si no -1.
        for (int i = 0; i < vertexQuantity; i++) {
            for (int j = 0; j < vertexQuantity; j++) {
                floydMatrix[i][j] = costs[i][j];
                P[i][j] = (i != j && costs[i][j] < INFINITO) ? i : -1;
            }
        }

        // El vértice intermedio k debe ir en el bucle EXTERNO para que las mejoras
        // de cada k se propaguen al resto de los pares de vértices.
        for (int k = 0; k < vertexQuantity; k++) {
            for (int i = 0; i < vertexQuantity; i++) {
                for (int j = 0; j < vertexQuantity; j++) {
                    // Solo se relaja si ambos tramos intermedios existen (distintos de INFINITO).
                    if (floydMatrix[i][k] != INFINITO && floydMatrix[k][j] != INFINITO &&
                        floydMatrix[i][j] > floydMatrix[i][k] + floydMatrix[k][j]) {
                        
                        // Se encontró un camino más corto pasando por k: se actualiza la
                        // distancia y el predecesor (origen del camino) del par (i, j).
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

    // Imprime una matriz, mostrando INF en los lugares donde no hay camino.
    private void mostrarMatriz(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print((matrix[i][j] == INFINITO ? "INF" : matrix[i][j]) + "\t");
            }
            System.out.println();
        }
    }

    // Clase Vertex comentada: la versión que se usa está en el archivo Vertex.java
    // (este bloque quedó de una versión anterior).
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
