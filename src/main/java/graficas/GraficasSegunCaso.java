package graficas;

import herramientas.LeerResultados;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.*;

/**
 * Clase para graficar los resultados de los algoritmos segun un caso especifico y segun,
 * un lenguaje especifico.
 * <p>
 * Implementado por:
 *  - Santiago Garcia Cañas
 *  - Sebastian Carmona Tapasco
 *  - Nodier Alberto Alzate Solano
 */
public class GraficasSegunCaso {

    public static void main(String[] args) {

        // Solicitar al usuario que ingrese un número del 1 al 2 para seleccionar el lenguaje
        String input1 = JOptionPane.showInputDialog(null, """
                Ingrese un número del 1 al 2:
                1. Java
                2. Python""");

        int numero1 = Integer.parseInt(input1);

        // Determinar el nombre del lenguaje seleccionado
        String lenguaje = switch (numero1) {
            case 1 -> "Java";
            case 2 -> "Python";
            default -> "Número fuera de rango";
        };

        // Solicitar al usuario que ingrese el tamaño para seleccionar
        String input2 = JOptionPane.showInputDialog(null, """
                Ingrese un número del 1 al 8:
                1. 64
                2. 128
                3. 256
                4. 512
                5. 1024
                6. 2048
                7. 4096
                8. 8192""");

        int numero2 = Integer.parseInt(input2);

        // Determinar el nombre del lenguaje seleccionado
        int caso = switch (numero2) {
            case 1 -> 64;
            case 2 -> 128;
            case 3 -> 256;
            case 4 -> 512;
            case 5 -> 1024;
            case 6 -> 2048;
            case 7 -> 4096;
            case 8 -> 8192;
            default -> 0;
        };

        // Leer los datos del archivo y almacenarlos en un mapa
        Map<String, Double> dataMap = LeerResultados.readDataFromFileSpecific("C:\\Users\\santi\\Desktop\\Proyecto Algoritmos - Graficar\\GraficasAlgoritmos\\src\\main\\resources\\resultados_"+lenguaje+".txt", caso);

        // Crear un conjunto de datos para la gráfica
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : dataMap.entrySet()) {
            dataset.addValue(entry.getValue(), "Tiempo de Ejecución", entry.getKey());
        }

        // Crear la gráfica de barras
        JFreeChart barChart = ChartFactory.createBarChart(
                "Tiempo de Ejecución de los Algoritmos para " + lenguaje + " (Tamaño:"+ caso+")",
                "Algoritmo",
                "Tiempo de Ejecución (segundos)",
                dataset);

        // Mostrar la gráfica en una ventana
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame("Gráfico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }


}
