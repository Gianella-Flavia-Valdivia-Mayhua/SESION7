package Laboratorio07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Ej04_CanalStreamingModelo {

    private final String archivo = "canales_streaming.txt";

    private List<Ej04_CanalStreaming> canales;

    // Constructor
    public Ej04_CanalStreamingModelo() {
        canales = new ArrayList<>();
    }

    // Leer los registros del archivo
    public List<Ej04_CanalStreaming> leerRegistros()
            throws IOException {

        File fichero = new File(archivo);

        // Crear el archivo si todavía no existe
        if (!fichero.exists()) {
            fichero.createNewFile();
        }

        canales.clear();

        try (BufferedReader lector =
                new BufferedReader(new FileReader(fichero))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {

                numeroLinea++;

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split("\\|", -1);

                if (datos.length != 3) {
                    throw new IOException(
                        "Formato incorrecto en la linea "
                        + numeroLinea
                    );
                }

                try {

                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    double precio = Double.parseDouble(datos[2]);

                    if (buscarRegistro(id) != null) {
                        throw new IOException(
                            "ID duplicado en la linea " + numeroLinea
                        );
                    }

                    canales.add(
                        new Ej04_CanalStreaming(id, nombre, precio)
                    );

                } catch (NumberFormatException
                        | IllegalArgumentException e) {

                    throw new IOException(
                        "Datos invalidos en la linea " + numeroLinea,
                        e
                    );
                }
            }
        }

        return new ArrayList<>(canales);
    }

    // Agregar un registro
    public boolean agregarRegistro(Ej04_CanalStreaming canal)
            throws IOException {

        if (buscarRegistro(canal.getId()) != null) {
            return false;
        }

        canales.add(canal);
        guardarCambios();

        return true;
    }

    // Buscar por identificador
    public Ej04_CanalStreaming buscarRegistro(int id) {

        for (Ej04_CanalStreaming canal : canales) {

            if (canal.getId() == id) {
                return canal;
            }
        }

        return null;
    }

    // Eliminar por identificador
    public boolean eliminarRegistro(int id) throws IOException {

        Ej04_CanalStreaming canal = buscarRegistro(id);

        if (canal == null) {
            return false;
        }

        canales.remove(canal);
        guardarCambios();

        return true;
    }

    // Listar todos los registros
    public List<Ej04_CanalStreaming> obtenerRegistros() {

        return new ArrayList<>(canales);
    }

    // Guardar todos los cambios en el archivo
    private void guardarCambios() throws IOException {

        try (PrintWriter escritor =
                new PrintWriter(new FileWriter(archivo))) {

            for (Ej04_CanalStreaming canal : canales) {

                escritor.println(
                    canal.getId() + "|"
                    + canal.getNombre() + "|"
                    + canal.getPrecioSuscripcion()
                );
            }

            escritor.flush();

            if (escritor.checkError()) {
                throw new IOException(
                    "No se pudieron guardar los registros."
                );
            }
        }
    }
}
