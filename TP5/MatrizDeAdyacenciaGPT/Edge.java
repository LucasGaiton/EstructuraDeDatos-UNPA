package MatrizDeAdyacenciaGPT;

public class Edge {
    private int destination; // Índice del vértice destino
    private int weight;      // Peso de la arista

    public Edge(int destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{destination=" + destination + ", weight=" + weight + "}";
    }
    
}
