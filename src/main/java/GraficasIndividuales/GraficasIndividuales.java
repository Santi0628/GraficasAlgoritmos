package GraficasIndividuales;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

/**
 *
 */
public class GraficasIndividuales {
    public static void main(String[] args) {
        // Nombre del algoritmo que se quiere graficar
        String algoritmo = "Parallel block_III";
        // Nombre del lenguaje que se quiere graficar
        String lenguaje = "Java";

        // Lee los datos del archivo y almacénalos en un mapa
        Map<Integer, Double> dataMap = readDataFromFile("C:\\Users\\santi\\IdeaProjects\\GraficasAlgoritmos\\src\\main\\resources\\resultados_"+lenguaje+".txt", algoritmo);

        // Crea un conjunto de datos para la gráfica
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Double> entry : dataMap.entrySet()) {
            dataset.addValue(entry.getValue(), algoritmo, String.valueOf(entry.getKey()));
        }

        // Crea la gráfica de barras
        JFreeChart barChart = ChartFactory.createBarChart(
                "Tiempo de Ejecución de " + algoritmo,
                "Tamaño",
                "Tiempo de Ejecución",
                dataset);

        // Muestra la gráfica en una ventana
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame("Gráfico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private static Map<Integer, Double> readDataFromFile(String filename, String algoritmo) {
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
