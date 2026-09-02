
public class Vertex {
    private Object element;
    private Edge edge;

    public Vertex() {
        this.element = null;
        this.edge = null;
    }

    public Object getElement() {
        return this.element;
    }

    public Edge getEdge() {
        return this.edge;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

}
