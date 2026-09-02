

public class Nodo {
    private Nodo sig;
    private int info;
    public Nodo(int info){
        this.info = info;
        this.sig = null;
    }
    public void setInfo(int info) {
        this.info = info;
    }
    public int getInfo() {
        return info;
    }
    public void setSig(Nodo sig) {
        this.sig = sig;
    }
    public Nodo getSig() {
        return sig;
    }
}
