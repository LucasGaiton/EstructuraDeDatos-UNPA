



public class Edge {
    private int position;
    private Edge edge;
    private int cost;

    public Edge() {
        this.position = 0;
        this.edge = null;
        this.cost = 0;
    }

    public Edge(int cost) {
        this.position = 0;
        this.edge = null;
        this.cost = cost;
    }

    public int getPosition() {
        return this.position;
    }

    public Edge getEdge() {
        return this.edge;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }
    public int getCost() {
        return cost;
    }
    

}