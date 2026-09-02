import java.util.List;

public class MainD {
    public static void main(String[] args) {
        Graph g1 = new Graph(4);
        g1.insertVertex("a");
        g1.insertVertex("b");
        g1.insertVertex("c");
        g1.insertVertex("d");
        g1.insertEdge("a", "b",2);
        g1.insertEdge("a", "c",3);
        g1.insertEdge("b", "d",1);
        g1.insertEdge("d", "a",6);

        List<Integer> list = g1.dijkstraAlgorithm("a");
        for (Integer elem : list) {
            System.out.println(elem);
        }




    
        
    }
}
