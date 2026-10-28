package TP5.MatrizDeAdyacencia;

public class MainD {
    public static void main(String[] args) {
        Graph graph = new Graph(6);

        graph.insertVertex(1);
        graph.insertVertex(2);
        graph.insertVertex(3);
        graph.insertVertex(4);
        graph.insertVertex(5);
        graph.insertVertex(6);

        graph.insertEdge(1, 2, 3);
        graph.insertEdge(1, 4, 12);
        graph.insertEdge(2, 6, 3);
        graph.insertEdge(2, 5, 1);
        graph.insertEdge(5, 4, 7);
        graph.insertEdge(5, 6, 1);
        graph.insertEdge(6, 3, 2);
        graph.insertEdge(3, 2, 4);

        // System.out.println("Dijkstra desde 1:");
        // System.out.println(graph.dijkstraAlgorithm(1));

        System.out.println();
        System.out.println("Floyd:");
        graph.floyd();
    }
}