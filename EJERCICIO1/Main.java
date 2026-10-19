package ejercicios;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Gestor gestor = new Gestor();
        int opcion;

        do {
            System.out.println("\n========================================");
            System.out.println("   GESTOR DE PERSONAJES DE VIDEOJUEGO");
            System.out.println("========================================");
            System.out.println("1. Listar personajes");
            System.out.println("2. Agregar personaje");
            System.out.println("3. Modificar personaje (completo)");
            System.out.println("4. Borrar personaje");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, ingrese un número válido: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    gestor.mostrarPersonajes();
                    break;
                case 2:
                    try {
                        System.out.print("Ingrese nombre del personaje: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese vida (>0): ");
                        int vida = scanner.nextInt();
                        System.out.print("Ingrese ataque (>0): ");
                        int ataque = scanner.nextInt();
                        System.out.print("Ingrese defensa (>0): ");
                        int defensa = scanner.nextInt();
                        System.out.print("Ingrese alcance (>0): ");
                        int alcance = scanner.nextInt();
                        scanner.nextLine();

                        Personaje nuevo = new Personaje(nombre, vida, ataque, defensa, alcance);
                        gestor.agregarPersonaje(nuevo);
                    } catch (Exception e) {
                        System.out.println("Error en los datos ingresados: " + e.getMessage());
                        scanner.nextLine();
                    }
                    break;
                case 3:
                    System.out.print("Ingrese el nombre del personaje a modificar: ");
                    String nombreMod = scanner.nextLine();
                    try {
                        System.out.print("Nueva vida (>0): ");
                        int vida = scanner.nextInt();
                        System.out.print("Nuevo ataque (>0): ");
                        int ataque = scanner.nextInt();
                        System.out.print("Nueva defensa (>0): ");
                        int defensa = scanner.nextInt();
                        System.out.print("Nuevo alcance (>0): ");
                        int alcance = scanner.nextInt();
                        scanner.nextLine();

                        gestor.modificarPersonaje(nombreMod, vida, ataque, defensa, alcance);
                    } catch (Exception e) {
                        System.out.println("Error en los datos: " + e.getMessage());
                        scanner.nextLine();
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el nombre del personaje a borrar: ");
                    String nombreDel = scanner.nextLine();
                    gestor.borrarPersonaje(nombreDel);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}
