package MatrizDeAdyacenciaGPT;
import java.util.ArrayList;
import java.util.List;
public class Vertex {
    private Object value;          // Valor del vértice
    private List<Edge> edges;      // Lista de aristas adyacentes

    public Vertex(Object value) {
        this.value = value;
        this.edges = new ArrayList<>();
    }
    public Object getValue() {
        return this.value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public List<Edge> getEdges() {
        return this.edges;
    }
    public void addEdge(int destination, int weight) {
        this.edges.add(new Edge(destination, weight));
    }

    @Override
    public String toString() {
        return "Vertex{value=" + value + ", edges=" + edges + "}";
    }





    
}
