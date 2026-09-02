package TP5.MatrizDeAdyacencia;

public class MainD {
    public static void main(String[] args) {
        Graph graph = new Graph(7);

        // Tabla de valores:
        // 1 = A
        // 2 = B
        // 3 = C
        // 4 = D
        // 5 = E
        // 6 = F
        // 7 = G

        graph.insertVertex(1);
        graph.insertVertex(2);
        graph.insertVertex(3);
        graph.insertVertex(4);
        graph.insertVertex(5); 
        graph.insertVertex(6);
        graph.insertVertex(7);


        graph.insertEdge(1, 2, 7);
        graph.insertEdge(1, 3, 6);
        graph.insertEdge(1, 4, 8);

        graph.insertEdge(2, 5, 2);

        graph.insertEdge(3, 5, 7);

        graph.insertEdge(4, 5, 3);
        graph.insertEdge(4, 6, 4);

        graph.insertEdge(5, 6, 3);
        graph.insertEdge(5, 7, 1);

        graph.insertEdge(6, 5, 3);
        graph.insertEdge(6, 7, 1);

        System.out.println("Dijkstra desde A:");
        System.out.println(graph.dijkstraAlgorithm(1));

        System.out.println();
        System.out.println("Floyd:");
        graph.floyd();
    }
}