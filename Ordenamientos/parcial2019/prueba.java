
import java.util.*;

public class prueba {

    public static class Usuario {

        private int dni;

        public Usuario(int dni) {
            this.dni = dni;
        }

        public int getDni() {
            return dni;
        }

    }

    public static Usuario[] shellSort(Usuario[] vec) {
        int interval = vec.length / 2; // Comenzar con un intervalo de la mitad del tamaño del array
        while (interval > 0) {
            // Realiza la ordenación para cada subarray de tamaño 'interval'
            for (int i = interval; i < vec.length; i++) {
                Usuario temp = vec[i]; // Almacenar el valor actual
                int j = i;

                // Comparar y mover los elementos del subarray
                while (j >= interval && vec[j - interval].dni > temp.dni) {
                    vec[j] = vec[j - interval]; // Mover el elemento hacia adelante
                    j -= interval; // Mover hacia atrás en el subarray
                }
                vec[j] = temp; // Colocar el elemento actual en su posición correcta
            }
            interval /= 2; // Reducir el intervalo
        }
        // for (int i = 0; i < vec.length; i++) {
        //     System.out.println(vec[i].dni);
        // }
        return vec;

    }

    public static LinkedList crearListaOrdenada(LinkedList l1, LinkedList l2, LinkedList l3) {
        Usuario[] aux = new Usuario [l1.size()+l2.size()+l3.size()];
        System.out.println(aux.length);
        int index = 0;

        
        for (int i = 0; i < l1.size(); i++) {
            aux[index] = (Usuario)l1.get(i);
            index++;

        }
        for (int i = 0; i < l2.size(); i++) {
            aux[index] = (Usuario)l2.get(i);
            index++;
        }
        for (int i = 0; i < l3.size(); i++) {
            aux[index] = (Usuario)l3.get(i);
            index++;
        }

        aux = shellSort(aux);
        LinkedList lista = new LinkedList<Usuario>();
        for (Usuario usuario : aux) {
            lista.add(usuario);
        }

        return lista;

    }

    public static void main(String[] args) {
        LinkedList l1 = new LinkedList<Usuario>();
        LinkedList l2 = new LinkedList<Usuario>();
        LinkedList l3 = new LinkedList<Usuario>();

        l1.add(new Usuario(48678222));
        l1.add(new Usuario(90678222));
        l1.add(new Usuario(12678222));

        l2.add(new Usuario(67678222));
        l2.add(new Usuario(88678222));
        l2.add(new Usuario(12678222));

        l3.add(new Usuario(11678222));
        l3.add(new Usuario(76678222));
        l3.add(new Usuario(45678222));
        System.out.println("pasa");
        LinkedList<Usuario> resul = crearListaOrdenada(l1, l2, l3);
        for(int i = 0 ; i< resul.size(); i++){
            System.out.println(resul.get(i).dni);
        }

    }

}
