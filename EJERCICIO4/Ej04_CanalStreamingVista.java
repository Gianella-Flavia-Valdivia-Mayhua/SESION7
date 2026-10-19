package Laboratorio07;

import java.util.List;
import java.util.Scanner;

public class Ej04_CanalStreamingVista {

    private Scanner scanner;

    public Ej04_CanalStreamingVista() {
        scanner = new Scanner(System.in);
    }

    // Mostrar menú principal
    public void mostrarMenu() {

        System.out.println("\n===== PLATAFORMA DE STREAMING =====");
        System.out.println("1. Listar canales");
        System.out.println("2. Agregar canal");
        System.out.println("3. Buscar canal por ID");
        System.out.println("4. Eliminar canal");
        System.out.println("5. Salir");
    }

    // Solicitar opcion
    public String solicitarOpcion() {

        System.out.print("Seleccione una opcion: ");
        return scanner.nextLine();
    }

    // Solicitar ID
    public int solicitarId() {

        System.out.print("Ingrese el ID: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    // Solicitar nombre
    public String solicitarNombre() {

        System.out.print("Ingrese el nombre del canal: ");
        return scanner.nextLine();
    }

    // Solicitar precio
    public double solicitarPrecio() {

        System.out.print(
            "Ingrese el precio de suscripcion (S/.): "
        );

        return Double.parseDouble(
            scanner.nextLine().trim().replace(',', '.')
        );
    }

    // Mostrar lista de canales
    public void mostrarRegistros(
            List<Ej04_CanalStreaming> canales) {

        if (canales.isEmpty()) {

            System.out.println("No hay canales registrados.");

        } else {

            System.out.println("\n===== CANALES REGISTRADOS =====");

            for (Ej04_CanalStreaming canal : canales) {
                System.out.println(canal);
            }
        }
    }

    // Mostrar un canal
    public void mostrarCanal(Ej04_CanalStreaming canal) {
        System.out.println(canal);
    }

    // Mostrar mensaje
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Cerrar Scanner
    public void cerrarScanner() {
        scanner.close();
    }
}
