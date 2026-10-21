package TP5.ListaDeAdyacencia;
import java.util.List;

public class MainD {
    public static void main(String[] args) {
        Graph g1 = new Graph(6);
        g1.insertVertex("1");
        g1.insertVertex("2");
        g1.insertVertex("4");
        g1.insertVertex("5");
        g1.insertVertex("6");
        g1.insertVertex("3");

        g1.insertEdge("1", "2", 3);
        g1.insertEdge("1", "4", 12);
        g1.insertEdge("2", "6", 3);
        g1.insertEdge("2", "5", 1);
        g1.insertEdge("5", "4", 7);
        g1.insertEdge("5", "6", 1);
        g1.insertEdge("6", "3", 2);
        g1.insertEdge("3", "2", 4);

        g1.depthFirstSearch("1");

    }
}
