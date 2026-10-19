package Laboratorio07;

import java.io.IOException;
import java.util.List;

public class Ej04_CanalStreamingControlador {

    private Ej04_CanalStreamingModelo modelo;
    private Ej04_CanalStreamingVista vista;

    public Ej04_CanalStreamingControlador(
            Ej04_CanalStreamingModelo modelo,
            Ej04_CanalStreamingVista vista) {

        this.modelo = modelo;
        this.vista = vista;
    }

    // Iniciar aplicación
    public void iniciar() {

        try {

            List<Ej04_CanalStreaming> canales =
                    modelo.leerRegistros();

            vista.mostrarMensaje(
                "Archivo cargado correctamente."
            );

            // Mostrar los registros al iniciar
            vista.mostrarRegistros(canales);

            boolean continuar = true;

            while (continuar) {

                vista.mostrarMenu();

                String opcion = vista.solicitarOpcion();

                switch (opcion) {

                    case "1":
                        listarCanales();
                        break;

                    case "2":
                        agregarCanal();
                        break;

                    case "3":
                        buscarCanal();
                        break;

                    case "4":
                        eliminarCanal();
                        break;

                    case "5":
                        vista.mostrarMensaje(
                            "Saliendo del programa..."
                        );
                        continuar = false;
                        break;

                    default:
                        vista.mostrarMensaje(
                            "Opcion no valida."
                        );
                }
            }

        } catch (IOException e) {

            vista.mostrarMensaje(
                "Error de archivo: " + e.getMessage()
            );

        } finally {

            vista.cerrarScanner();
        }
    }

    // Listar canales
    private void listarCanales() {

        vista.mostrarRegistros(
            modelo.obtenerRegistros()
        );
    }

    // Agregar canal
    private void agregarCanal() {

        try {

            int id = vista.solicitarId();
            String nombre = vista.solicitarNombre();
            double precio = vista.solicitarPrecio();

            Ej04_CanalStreaming canal =
                    new Ej04_CanalStreaming(id, nombre, precio);

            boolean agregado = modelo.agregarRegistro(canal);

            if (agregado) {

                vista.mostrarMensaje(
                    "Canal agregado y guardado correctamente."
                );

            } else {

                vista.mostrarMensaje(
                    "Ya existe un canal con ese ID."
                );
            }

        } catch (NumberFormatException e) {

            vista.mostrarMensaje(
                "Error: el ID debe ser entero y el precio numerico."
            );

        } catch (IllegalArgumentException e) {

            vista.mostrarMensaje(
                "Error en los datos: " + e.getMessage()
            );

        } catch (IOException e) {

            vista.mostrarMensaje(
                "Error al guardar: " + e.getMessage()
            );
        }
    }

    // Buscar canal
    private void buscarCanal() {

        try {

            int id = vista.solicitarId();

            Ej04_CanalStreaming canal =
                    modelo.buscarRegistro(id);

            if (canal != null) {

                vista.mostrarCanal(canal);

            } else {

                vista.mostrarMensaje(
                    "No se encontro un canal con ese ID."
                );
            }

        } catch (NumberFormatException e) {

            vista.mostrarMensaje(
                "El ID debe ser un numero entero."
            );
        }
    }

    // Eliminar canal
    private void eliminarCanal() {

        try {

            int id = vista.solicitarId();

            boolean eliminado = modelo.eliminarRegistro(id);

            if (eliminado) {

                vista.mostrarMensaje(
                    "Canal eliminado correctamente."
                );

            } else {

                vista.mostrarMensaje(
                    "No existe un canal con ese ID."
                );
            }

        } catch (NumberFormatException e) {

            vista.mostrarMensaje(
                "El ID debe ser un numero entero."
            );

        } catch (IOException e) {

            vista.mostrarMensaje(
                "Error al actualizar el archivo: " + e.getMessage()
            );
        }
    }
}
