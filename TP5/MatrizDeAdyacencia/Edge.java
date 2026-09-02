package TP5.MatrizDeAdyacencia;

public class Edge {
    private int position;
    private Edge edge;
    private int coste;
    Edge() {
        position = 0;
        coste=0;
        edge = null;
    }
    Edge(int coste) {
        this.position = 0;
        this.coste = coste;
        this.edge = null;
    }
    public int getPosition() {
        return position;
    }
    public int getCoste() {
        return this.coste;
    }
    public void setCoste(int coste) {
        this.coste = coste;
    }
    public Edge getEdge() {
        return edge;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public void setEdge(Edge edge) {
        this.edge = edge;
    }
}