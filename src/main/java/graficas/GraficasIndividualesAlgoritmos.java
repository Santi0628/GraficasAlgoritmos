package graficas;


import java.util.Map;
import herramientas.LeerResultados;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

/**
 * Clase para graficar de manera individual cada algoritmo, se pide
 * por parametro el lenguaje a examinar y el algoritmo a examinar.
 * <p>
 * Implementado por:
 *  - Santiago Garcia Cañas
 *  - Sebastian Carmona Tapasco
 *  - Nodier Alberto Alzate Solano
 */
public class GraficasIndividualesAlgoritmos {
    public static void main(String[] args) {

        // Se pide el lenguaje por un JOption
        String input1 = JOptionPane.showInputDialog(null, """
                Ingrese un número del 1 al 2:
                1. Java
                2. Python""");
        int numero1 = Integer.parseInt(input1);

        // Nombre del lenguaje que se quiere graficar
        String lenguaje = switch (numero1) {
            case 1 -> "Java";
            case 2 -> "Python";
            default -> "Número fuera de rango";
        };

        String input2 = JOptionPane.showInputDialog(null, """
                Ingrese un número del 1 al 16:
                1. NaivOnArray
                2. NaivLoopUnrollingTwo
                3. NaivLoopUnrollingFour
                4. WinogradOriginal
                5. WinogradScaled
                6. StrassenNaiv
                7. StrassenWinograd
                8. Sequential block_III
                9. Parallel block_III
                10. Enhanced Parallel Block_III
                11. SequentialBlock_IV
                12. ParallelBlock_IV
                13. Enhanced Parallel Block_IV
                14. SequentialBlock_V
                15. ParallelBlock_V
                16. Número fuera de rango""");

        // Convertir el input a un número entero
        int numero2 = Integer.parseInt(input2);

        // Variable para almacenar el nombre del algoritmo
        String algoritmo = switch (numero2) {
            case 1 -> "NaivOnArray";
            case 2 -> "NaivLoopUnrollingTwo";
            case 3 -> "NaivLoopUnrollingFour";
            case 4 -> "WinogradOriginal";
            case 5 -> "WinogradScaled";
            case 6 -> "StrassenNaiv";
            case 7 -> "StrassenWinograd";
            case 8 -> "Sequential block_III";
            case 9 -> "Parallel block_III";
            case 10 -> "Enhanced Parallel Block_III";
            case 11 -> "SequentialBlock_IV";
            case 12 -> "ParallelBlock_IV";
            case 13 -> "Enhanced Parallel Block_IV";
            case 14 -> "SequentialBlock_V";
            case 15 -> "ParallelBlock_V";
            default -> "Número fuera de rango";
        };

        // Lee los datos del archivo y almacénalos en un mapa, buscando por lenguaje y algoritmo
        Map<Integer, Double> dataMap = LeerResultados.readDataFromFileGeneral("C:\\Users\\santi\\Desktop\\Proyecto Algoritmos - Graficar\\GraficasAlgoritmos\\src\\main\\resources\\resultados_"+lenguaje+".txt", algoritmo);

        // Crea un conjunto de datos para la gráfica
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Double> entry : dataMap.entrySet()) {
            dataset.addValue(entry.getValue(), algoritmo, String.valueOf(entry.getKey()));
        }

        // Crea la gráfica de barras
        JFreeChart barChart = ChartFactory.createBarChart(
                "Tiempo de Ejecución de " + algoritmo,
                "Tamaño",
                "Tiempo de Ejecución (segundos)",
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


}
