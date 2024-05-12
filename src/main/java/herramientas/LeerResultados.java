package herramientas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Herramieta usada para leer unos datos de los txt de los resultados.
 * <p>
 * Implementado por:
 *  - Santiago Garcia Cañas
 *  - Sebastian Carmona Tapasco
 *  - Nodier Alberto Alzate Solano
 */
public class LeerResultados {

    /**
     * Herramienta para buscar en un txt especificado segun un caso de TE especifico,
     * con el fin de extraer el nombre y los TE.
     * @param filename representa el nombre del archivo a buscar
     * @param caso representa el tamaño de caso a buscar
     * @return un Map con (String --> nombre del algoritmo) y
     * (Double --> TE del algoritmo).
     */
    public static Map<String, Double> readDataFromFileSpecific(String filename, int caso) {
        Map<String, Double> dataMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean readingAlgorithmData = false;
            while ((line = br.readLine()) != null) {
                if (line.matches("\\d+;")) {
                    int size = Integer.parseInt(line.replaceAll(";", ""));
                    readingAlgorithmData = size == caso;
                } else if (readingAlgorithmData) {
                    String[] parts = line.split(",");
                    String algoritmo = parts[0];
                    double tiempo = Double.parseDouble(parts[1].replaceAll(";", ""));
                    dataMap.put(algoritmo, tiempo);
                }
            }
            // Ordenar el mapa por los valores (tiempos)
            dataMap = sortByValue(dataMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    // Método para ordenar el mapa por valores (tiempos) en orden ascendete
    public static Map<String, Double> sortByValue(Map<String, Double> unsortedMap) {
        List<Map.Entry<String, Double>> list = new LinkedList<>(unsortedMap.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getValue));
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    /**
     * Herramienta usada para leer y buscar de un archivo especificado un algoritmo,
     * extrayendo todos sus TE de todos lo casos.
     * @param filename representa la ruta al archivo a leer y buscar.
     * @param algoritmo representa el algoritmo al cual se le quieren buscar sus TE en todos los casos.
     * @return un Map con los tamaños (Integer) y sus TE (Double).
     */
    public static Map<Integer, Double> readDataFromFileGeneral(String filename, String algoritmo) {
        Map<Integer, Double> dataMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int size = 0;
            double time = 0.0;
            boolean readingAlgorithmData = false;
            while ((line = br.readLine()) != null) {
                if (line.matches("\\d+;")) { // Encuentra un tamaño
                    size = Integer.parseInt(line.replaceAll(";", ""));
                    readingAlgorithmData = false;
                } else if (line.contains(algoritmo) && !readingAlgorithmData) { // Encuentra el algoritmo especificado
                    String[] parts = line.split(",");
                    time = Double.parseDouble(parts[1].replaceAll(";", ""));
                    dataMap.put(size, time);
                    readingAlgorithmData = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

}
