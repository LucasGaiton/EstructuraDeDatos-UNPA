
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
    private Vertex[] vertices;
    private int vertexPosition;
    private int vertexQuantity;

    public Graph(int quantity) {
        this.vertexQuantity = quantity;
        this.vertices = new Vertex[this.vertexQuantity];
        this.vertexPosition = 0;
    }

    public void insertVertex(Object element) {
        this.vertices[this.vertexPosition] = new Vertex();
        this.vertices[this.vertexPosition].setElement(element);
        this.vertexPosition++;
    }

    public void insertEdge(Object originElement, Object finishElement, int cost) {
        int originPosition = getVertexIndex(originElement);
        int finishPosition = getVertexIndex(finishElement);
        Edge edge = this.vertices[originPosition].getEdge();
        Edge newEdge = new Edge(cost);
        newEdge.setPosition(finishPosition);
        if (edge == null) {
            this.vertices[originPosition].setEdge(newEdge);
        } else {
            while (edge.getEdge() != null) {
                edge = edge.getEdge();
            }
            edge.setEdge(newEdge);
        }
    }

    private int getVertexIndex(Object element) {
        for (int i = 0; i < this.vertexPosition; i++) {
            if (this.vertices[i].getElement().equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public void depthFirstSearch(Object element) {
        ArrayList<Integer> visited = new ArrayList<>(); // Lista para almacenar los vértices visitados.
        depthFirst(getVertexIndex(element), visited); // Inicia la búsqueda en profundidad.
    }

    private void depthFirst(int element, ArrayList<Integer> visited) {
        // Imprime el vértice actual
        System.out.print(this.vertices[element].getElement() + " ");

        // Marca el vértice como visitado
        visited.add(element);

        // Obtiene los adyacentes del vértice actual
        Iterator<Integer> adjs = adjacents(element).iterator();

        // Itera sobre los vértices adyacentes
        while (adjs.hasNext()) {
            int adjOther = adjs.next(); // Obtiene el índice del siguiente vértice adyacente

            // Si el vértice no ha sido visitado, llama recursivamente a depthFirst
            if (!visited.contains(adjOther)) {
                depthFirst(adjOther, visited);
            }
        }
    }

    private List<Integer> adjacents(int element) {
        List<Integer> adjacentVertices = new ArrayList<>(); // Lista para almacenar vértices adyacentes.

        Edge position = this.vertices[element].getEdge(); // Obtiene la primera arista del vértice.

        // Itera sobre todas las aristas conectadas al vértice.
        while (position != null) {
            adjacentVertices.add(position.getPosition()); // Agrega el índice del vértice adyacente.
            position = position.getEdge(); // Avanza a la siguiente arista.
        }

        return adjacentVertices; // Devuelve la lista de adyacentes.
    }

    public void breadhFirstSearch(Object element) {
        breadhFirst(getVertexIndex(element)); // Llama al método BFS privado con el índice del vértice inicial.
    }

    private void breadhFirst(int element) {
        List<Integer> visited = new ArrayList<>();
        Queue<Integer> explore = new LinkedList<>();

        explore.add(element);
        visited.add(element);

        while (!explore.isEmpty()) {
            int vertexOther = explore.poll();
            System.out.print(vertices[vertexOther].getElement() + " ");

            for (int adj : adjacents(vertexOther)) {
                if (!visited.contains(adj)) {
                    explore.add(adj);
                    visited.add(adj);
                }
            }
        }
    }

    // Método Dijkstra principal
    public List<Integer> dijkstraAlgorithm(Object vertex) {
        int startVertexIndex = getVertexIndex(vertex);
        if (startVertexIndex == -1) {
            throw new IllegalArgumentException("El vértice no existe.");
        }
        return dijkstra(startVertexIndex);
    }

    private List<Integer> dijkstra(int vertex) {
        List<Integer> distance = new ArrayList<>(); // Lista de distancias mínimas.
        List<Integer> toVisit = new ArrayList<>(); // Vértices por visitar.

        // Inicialización de las distancias.
        for (int i = 0; i < vertexQuantity; i++) {
            if (i == vertex) {
                distance.add(0); // Distancia al vértice inicial es 0.
            } else {
                distance.add(1000000); // Distancia inicial "infinito".
            }
            toVisit.add(i); // Agrega todos los vértices a la lista de no visitados.
        }

        // Mientras queden vértices por visitar...
        while (!toVisit.isEmpty()) {
            // Encuentra el vértice con la menor distancia.
            int u = minimum(distance, toVisit);
            toVisit.remove(Integer.valueOf(u)); // Marca el vértice como visitado.

            int du = distance.get(u); // Distancia mínima al vértice `u`.

            // Si el vértice `u` es alcanzable...
            if (du != 1000000) {
                for (int w : adjacents(u)) { // Itera sobre los vértices adyacentes.
                    if (toVisit.contains(w)) { // Solo procesa vértices no visitados.
                        int cuw = this.getEdge(u, w); // Costo de la arista `u -> w`.
                        // Si se encuentra un camino más corto, actualiza la distancia.
                        if (du + cuw < distance.get(w)) {
                            distance.set(w, du + cuw);
                        }
                    }
                }
            }
        }

        return distance; // Retorna las distancias mínimas desde el vértice inicial.
    }

    private int minimum(List<Integer> distance, List<Integer> toVisit) {
        int minVertex = toVisit.get(0); // Inicializa con el primer vértice de `toVisit`.
        int minValue = distance.get(minVertex);

        for (int vertex : toVisit) {
            if (distance.get(vertex) < minValue) {
                minValue = distance.get(vertex);
                minVertex = vertex;
            }
        }

        return minVertex;
    }

    private int getEdge(int u, int w) {
        Edge arista = vertices[u].getEdge();
        while (arista != null) {
            if (arista.getPosition() == w) {
                return arista.getCost();
            }
            arista = arista.getEdge();
        }
        return -1;
    }
    

    

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Graph:\n");

        for (int i = 0; i < vertexPosition; i++) {
            Vertex vertex = vertices[i];
            result.append("Vertex ").append(vertex.getElement()).append(": ");

            Edge edge = vertex.getEdge();
            if (edge == null) {
                result.append("No edges");
            } else {
                while (edge != null) {
                    result.append(vertices[edge.getPosition()].getElement()).append(" -> ");
                    edge = edge.getEdge();
                }
                // Remueve el último " -> "
                result.setLength(result.length() - 4);
            }
            result.append("\n");
        }

        return result.toString();
    }

}
