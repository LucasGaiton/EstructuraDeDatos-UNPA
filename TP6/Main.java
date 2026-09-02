import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrieMatrix trie = new TrieMatrix();
        TrieMatrix trieReversed = new TrieMatrix(); // Trie para búsqueda por sufijos

        while (true) {
            System.out.println("\n--- Árbol Trie: Opciones ---");
            System.out.println("1. Insertar palabra");
            System.out.println("2. Contar palabras almacenadas");
            System.out.println("3. Listar todas las palabras");
            System.out.println("4. Buscar palabras con prefijo");
            System.out.println("5. Buscar palabras similares");
            System.out.println("6. Contar prefijos distintos");
            System.out.println("7. Buscar palabras por sufijos");
            System.out.println("8. Salir");
            System.out.print("Selecciona una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1: // Insertar palabra
                    System.out.print("Introduce la palabra a insertar: ");
                    String word = scanner.nextLine().toLowerCase();
                    trie.insertWord(word);
                    trieReversed.insertWordReversed(word);
                    System.out.println("Palabra insertada correctamente.");
                    break;

                case 2: // Contar palabras almacenadas
                    System.out.println("Cantidad de palabras almacenadas: " + trie.countWords());
                    break;

                case 3: // Listar todas las palabras
                    List<String> allWords = trie.listWords();
                    System.out.println("Palabras almacenadas:");
                    for (String w : allWords) {
                        System.out.println(w);
                    }
                    break;

                case 4: // Buscar palabras con prefijo
                    System.out.print("Introduce el prefijo: ");
                    String prefix = scanner.nextLine().toLowerCase();
                    List<String> wordsWithPrefix = trie.listWordsWithPrefix(prefix);
                    System.out.println("Palabras con el prefijo '" + prefix + "':");
                    for (String w : wordsWithPrefix) {
                        System.out.println(w);
                    }
                    break;

                case 5: // Buscar palabras similares
                    System.out.print("Introduce la palabra a buscar: ");
                    String searchWord = scanner.nextLine().toLowerCase();
                    List<String> similarWords = trie.searchSimilarWords(searchWord);
                    if (similarWords.isEmpty()) {
                        System.out.println("No se encontraron palabras similares.");
                    } else {
                        System.out.println("Palabras similares encontradas:");
                        for (String w : similarWords) {
                            System.out.println(w);
                        }
                    }
                    break;

                case 6: // Contar prefijos distintos
                    System.out.println("Cantidad de prefijos distintos: " + trie.countPrefixes());
                    break;

                // case 7: // Buscar palabras por sufijos
                //     System.out.print("Introduce el sufijo: ");
                //     String suffix = scanner.nextLine().toLowerCase();
                //     List<String> wordsWithSuffix = trieReversed.searchBySuffix(suffix);
                //     System.out.println("Palabras con el sufijo '" + suffix + "':");
                //     for (String w : wordsWithSuffix) {
                //         System.out.println(w);
                //     }
                //     break;

                case 8: // Salir
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        }
    }
}
