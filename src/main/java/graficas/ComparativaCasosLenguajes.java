package graficas;

import herramientas.LeerResultados;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Clase para comparar el TE segun el caso en ambos lenguajes,
 * se pide por parametro el caso a examinar y se grafica el rendimiento
 * de los algoritmos en ambos lenguajes para dicho caso, permitiendo una
 * mejor comparacion.
 * <p>
 * Implementado por:
 * - Santiago Garcia Cañas
 * - Sebastian Carmona Tapasco
 * - Nodier Alberto Alzate Solano
 */
public class ComparativaCasosLenguajes {

    public static void main(String[] args) {

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
        Map<String, Double> dataMapJava = LeerResultados.readDataFromFileSpecific("C:\\Users\\santi\\Desktop\\Proyecto Algoritmos - Graficar\\GraficasAlgoritmos\\src\\main\\resources\\resultados_Java.txt", caso);
        Map<String, Double> dataMapPython = LeerResultados.readDataFromFileSpecific("C:\\Users\\santi\\Desktop\\Proyecto Algoritmos - Graficar\\GraficasAlgoritmos\\src\\main\\resources\\resultados_Python.txt", caso);


        DefaultCategoryDataset dataset = createDataset(dataMapJava, dataMapPython);
        displayChart("Comparativa de tiempos de ejecución para el caso: "+caso, dataset);

    }

    // Creando el dateset a ser graficaso
    private static DefaultCategoryDataset createDataset(Map<String, Double> dataMapJava, Map<String, Double> dataMapPython) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Double> entry : dataMapJava.entrySet()) {
            String algoritmo = entry.getKey();
            double tiempoJava = entry.getValue();
            Double tiempoPython = dataMapPython.get(algoritmo); // Obtenemos el tiempo de Python para el mismo algoritmo

            // Verificar si tiempoPython es nulo
            if (tiempoPython != null) {
                dataset.addValue(tiempoJava, "Java", algoritmo);
                dataset.addValue(tiempoPython, "Python", algoritmo);
            } else {
                // Si tiempoPython es nulo, puedes manejarlo de alguna manera, como ignorar ese algoritmo
                System.out.println("No se encontró el tiempo de ejecución de Python para el algoritmo: " + algoritmo);
            }
        }

        return dataset;
    }

    // Configurando la grafica
    private static void displayChart(String title, DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                "Algoritmo",
                "Tiempo de Ejecución (segundos)",
                dataset
        );

        // Personalizar la apariencia del gráfico
        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setPaint(Color.black);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.black);

        // Mostrar el gráfico en un panel
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}