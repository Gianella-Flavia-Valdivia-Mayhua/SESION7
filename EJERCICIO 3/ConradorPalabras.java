package Laboratorio07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ContadorPalabras {

    public static void main(String[] args) {

        boolean procesado = false;

        // Permitir seleccionar otro archivo si ocurre un error
        while (!procesado) {

            JFileChooser selector = new JFileChooser();
            selector.setDialogTitle("Seleccione un archivo de texto");

            int resultado = selector.showOpenDialog(null);

            // Si el usuario cancela, terminar el programa
            if (resultado != JFileChooser.APPROVE_OPTION) {
                System.out.println("No se selecciono ningun archivo.");
                return;
            }

            File archivo = selector.getSelectedFile();

            // Verificar que el archivo exista y se pueda leer
            if (!archivo.exists()
                    || !archivo.isFile()
                    || !archivo.canRead()) {

                JOptionPane.showMessageDialog(
                    null,
                    "El archivo no existe o no se puede leer. Seleccione otro.",
                    "Archivo no valido",
                    JOptionPane.ERROR_MESSAGE
                );

                continue;
            }

            try {

                analizarArchivo(archivo);
                procesado = true;

            } catch (IOException e) {

                JOptionPane.showMessageDialog(
                    null,
                    "Error al leer el archivo: " + e.getMessage()
                    + "\nSeleccione otro archivo.",
                    "Error de lectura",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // Leer y analizar el archivo
    public static void analizarArchivo(File archivo)
            throws IOException {

        int totalLineas = 0;
        int totalPalabras = 0;
        long totalCaracteres = 0;

        // Guardar cada palabra y cuantas veces aparece
        Map<String, Integer> frecuencias = new HashMap<>();

        // Leer el archivo linea por linea
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(archivo),
                    StandardCharsets.UTF_8))) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                totalLineas++;

                // No se cuentan los saltos de linea
                totalCaracteres += linea.length();

                int inicioPalabra = -1;

                // Identificar palabras mediante letras y digitos
                for (int i = 0; i < linea.length(); i++) {

                    char caracter = linea.charAt(i);

                    if (Character.isLetterOrDigit(caracter)) {

                        if (inicioPalabra == -1) {
                            inicioPalabra = i;
                        }

                    } else {

                        if (inicioPalabra != -1) {

                            String palabra = linea.substring(
                                inicioPalabra, i
                            );

                            registrarPalabra(palabra, frecuencias);
                            totalPalabras++;

                            inicioPalabra = -1;
                        }
                    }
                }

                // Contar la ultima palabra de la linea
                if (inicioPalabra != -1) {

                    String palabra = linea.substring(inicioPalabra);

                    registrarPalabra(palabra, frecuencias);
                    totalPalabras++;
                }
            }
        }

        // Calcular promedio sin dividir entre cero
        double promedio = 0;

        if (totalLineas > 0) {
            promedio = (double) totalPalabras / totalLineas;
        }

        // Mostrar resultados
        System.out.println("\n===== CONTADOR DE PALABRAS =====");
        System.out.println("Archivo: " + archivo.getName());
        System.out.println("Total de lineas: " + totalLineas);
        System.out.println("Total de palabras: " + totalPalabras);
        System.out.println("Total de caracteres: " + totalCaracteres);

        System.out.printf(
            Locale.US,
            "Promedio de palabras por linea: %.2f%n",
            promedio
        );

        mostrarFrecuencias(frecuencias);
    }

    // Registrar una palabra y actualizar su frecuencia
    private static void registrarPalabra(
            String palabra,
            Map<String, Integer> frecuencias) {

        // No distinguir entre mayusculas y minusculas
        palabra = palabra.toLowerCase(Locale.ROOT);

        int cantidad = frecuencias.getOrDefault(palabra, 0);

        frecuencias.put(palabra, cantidad + 1);
    }

    // Mostrar las palabras ordenadas por frecuencia
    private static void mostrarFrecuencias(
            Map<String, Integer> frecuencias) {

        System.out.println("\n===== PALABRAS MAS FRECUENTES =====");

        if (frecuencias.isEmpty()) {
            System.out.println("No se encontraron palabras.");
            return;
        }

        List<Map.Entry<String, Integer>> lista =
                new ArrayList<>(frecuencias.entrySet());

        // Ordenar de mayor a menor frecuencia
        lista.sort((a, b) -> {

            int comparacion = Integer.compare(
                b.getValue(), a.getValue()
            );

            if (comparacion != 0) {
                return comparacion;
            }

            return a.getKey().compareTo(b.getKey());
        });

        for (Map.Entry<String, Integer> entrada : lista) {

            System.out.println(
                entrada.getKey() + ": "
                + entrada.getValue() + " vez/veces"
            );
        }
    }
}
