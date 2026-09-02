import java.util.*;
import java.lang.reflect.Field;

public class TpN2eje6 {
    public static void mergeSort(Object[] vec, String atributo, boolean ascendente) {
        Object[] tmp = new Object[vec.length];
        mergeSort(vec, tmp, 0, vec.length - 1, atributo, ascendente);
    }
    private static void mergeSort(Object[] vec, Object[] tmp, int left, int right, String atributo,
            boolean ascendente) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(vec, tmp, left, center, atributo, ascendente);
            mergeSort(vec, tmp, center + 1, right, atributo, ascendente);
            merge(vec, tmp, left, center, right, atributo, ascendente);
        }
    }

    private static void merge(Object[] vec, Object[] tmp, int left, int center, int right, String atributo,
            boolean ascendente) {
        int aptr = left;
        int bptr = center + 1;
        int cptr = left;

        while (aptr <= center && bptr <= right) {
            int comparison = compareByAttribute(vec[aptr], vec[bptr], atributo);
            if (ascendente) {
                if (comparison <= 0) {
                    tmp[cptr++] = vec[aptr++];
                } else {
                    tmp[cptr++] = vec[bptr++];
                }
            } else {
                if (comparison >= 0) {
                    tmp[cptr++] = vec[aptr++];
                } else {
                    tmp[cptr++] = vec[bptr++];
                }
            }
        }
        while (aptr <= center) {
            tmp[cptr++] = vec[aptr++];
        }
        while (bptr <= right) {
            tmp[cptr++] = vec[bptr++];
        }
        for (int i = left; i <= right; i++) {
            vec[i] = tmp[i];
        }
    }

    // Método que compara dos objetos según el atributo dado usando reflexión
    private static int compareByAttribute(Object o1, Object o2, String atributo) {
        try {
            // Obtener el campo (atributo) por su nombre
            Field field = o1.getClass().getDeclaredField(atributo);
            field.setAccessible(true); // Permite acceso a campos privados

            // Obtener los valores del atributo
            Comparable value1 = (Comparable) field.get(o1);
            Comparable value2 = (Comparable) field.get(o2);

            // Comparar los valores usando el método compareTo
            return value1.compareTo(value2);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // public static class ClaseObjeto{
    // int edad;
    // String nombre;
    // int salario;
    // public ClaseObjeto(int edad, String nombre, int salario){
    // this.edad=edad;
    // this.nombre=nombre;
    // this.salario=salario;
    // }

    // }

    // public static void Metodo(Vector<ClaseObjeto>Array, String atribute){

    // }
    // public static void main(String[] args) {

    // }
}