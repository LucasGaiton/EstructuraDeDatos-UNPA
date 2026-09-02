package punto1;

// Esta clase compara dos métodos de búsqueda sobre un arreglo de enteros:
// búsqueda secuencial (recorre elemento por elemento) y
// búsqueda binaria (requiere ordenar el arreglo previamente y divide el rango a la mitad en cada paso).

public class punto1 {
    private static void busquedaSecuencial(int[] elementos, int encontrar) {
        for (int i = 0; i < elementos.length - 1; i++) {
            if (elementos[i] == encontrar) {
                System.out.println("Encontrado ne posición: " + i);
                return;
            }
        }
        System.out.println("No encontrado");
    }

    private static void busquedaBinaria(int[] elementos, int encontrar) {
        for (int i = 0; i < elementos.length - 1; i++){
            for(int j = 0; j < elementos.length -1 - i; j++){
                if(elementos[j] > elementos[j + 1]){
                    int mayor = elementos[j];
                    int menor = elementos[j+1];
                    elementos[j] = menor;
                    elementos[j+1] = mayor;
                }   
            } 
        }

        int inicio= 0;
        int fin = elementos.length -1;
        while(inicio <= fin){
            int medio = (inicio + fin) / 2;
            if(encontrar > elementos[medio]){
                inicio = medio + 1;
            }
            else{
                if(encontrar < elementos[medio]){
                    fin = medio - 1;
                }else{
                    System.out.println("elemento encontrado "+ elementos[medio]);
                    return;
                }
            }
        }
        System.out.println("No encontrado");
    }

    public static void main(String[] args) {
        int[] elementos = { 10, 25, 66, 10, 8 };
        int encontrar = 25;
        busquedaSecuencial(elementos, encontrar);
        busquedaBinaria(elementos, encontrar);
    }
}
