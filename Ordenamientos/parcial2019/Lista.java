

public class Lista {
    private Nodo lista;
    private int cantNodo;

    public Lista() {
        this.lista = null;
        this.cantNodo=0;
    }

    public void agregar(int info) {
        this.cantNodo++;
        Nodo nuevo = new Nodo(info);
        if (lista == null) {
            lista = nuevo;
        } else {
            Nodo puntero = lista;
            while (puntero.getSig() != null) {
                puntero = puntero.getSig();
            }
            puntero.setSig(nuevo);
        }

    }
    public void agregarPrin(int info) {
        this.cantNodo++;
        Nodo nuevo = new Nodo(info);
        if (lista == null) {
            lista = nuevo;
        } else {
            nuevo.setSig(lista);
            lista = nuevo;
        }

    }

    public void agregarOR(int info) {
        this.cantNodo++;
        Nodo nuevo = new Nodo(info);
        if (lista == null) {
            lista = nuevo;
        } else {
            Nodo puntero = lista;
            boolean enc = false;
            if (lista.getInfo() > info) {
                nuevo.setSig(lista);
                lista = nuevo;
            } else {
                while (puntero.getSig() != null && !enc) {
                    if (puntero.getSig().getInfo() > info)
                        enc = true;
                    else
                        puntero = puntero.getSig();
                }
                if (enc) {
                    nuevo.setSig(puntero.getSig());
                    puntero.setSig(nuevo);
                } else
                    puntero.setSig(nuevo);

            }
        }

    }

    public int eliminarPrimero() {
        if (lista == null) {
            System.out.println("La lista esta vacia");
            return (Integer) null;
        } else {
            this.cantNodo--;
            int resul = lista.getInfo();
            this.eliminar(lista.getInfo());
            return resul;
        }
    }

    public void eliminar(int info) {
        if (lista == null) {
            System.out.println("La lista esta vacia");
        } else {
            
            Nodo puntero = lista;
            Nodo ant = null;
            boolean enc = false;
            while (puntero != null && !enc) {
                if (puntero.getInfo() == info){
                    enc = true;
                    this.cantNodo--;
                }
                else {
                    ant = puntero;
                    puntero = puntero.getSig();
                }
            }
            if (enc) {
                ant.setSig(puntero.getSig());
            }
        }
    }

    public void mostrar() {
        if (lista == null)
            System.out.println("La lista esta vacia");
        else {
            Nodo puntero = lista;
            while (puntero != null) {
                System.out.println(puntero.getInfo());
                puntero = puntero.getSig();
            }
        }
    }
    

}
