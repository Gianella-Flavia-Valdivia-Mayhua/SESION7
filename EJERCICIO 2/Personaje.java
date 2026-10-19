package ejercicios;

import java.io.Serializable;

public class Personaje implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private int vida;
    private int ataque;
    private int defensa;
    private int alcance;

    public Personaje(String nombre, int vida, int ataque, int defensa, int alcance) {
        setNombre(nombre);
        setVida(vida);
        setAtaque(ataque);
        setDefensa(defensa);
        setAlcance(alcance);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        if (vida <= 0) {
            throw new IllegalArgumentException("La vida debe ser un número entero mayor que cero.");
        }
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        if (ataque <= 0) {
            throw new IllegalArgumentException("El ataque debe ser un número entero mayor que cero.");
        }
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        if (defensa <= 0) {
            throw new IllegalArgumentException("La defensa debe ser un número entero mayor que cero.");
        }
        this.defensa = defensa;
    }

    public int getAlcance() {
        return alcance;
    }

    public void setAlcance(int alcance) {
        if (alcance <= 0) {
            throw new IllegalArgumentException("El alcance debe ser un número entero mayor que cero.");
        }
        this.alcance = alcance;
    }

    @Override
    public String toString() {
        return String.format("Personaje [Nombre: %s | Vida: %d | Ataque: %d | Defensa: %d | Alcance: %d]",
                nombre, vida, ataque, defensa, alcance);
    }
}

AP
package ejercicios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Gestor {
    private List<Personaje> personajes;
    private final String ARCHIVO = "personajes.dat";

    public Gestor() {
        personajes = new ArrayList<>();
        cargarDesdeArchivo();
        
        // Si el archivo está vacío, inicializamos con los valores por defecto de la guía
        if (personajes.isEmpty()) {
            personajes.add(new Personaje("Caballero", 4, 2, 4, 2));
            personajes.add(new Personaje("Guerrero", 2, 4, 2, 4));
            personajes.add(new Personaje("Arquero", 2, 4, 1, 8));
            guardarEnArchivo();
        }
    }

    // Guarda automáticamente los cambios en el archivo binario
    public void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(personajes);
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo: " + e.getMessage());
        }
    }

    // Carga los datos desde el archivo al iniciar el programa
    @SuppressWarnings("unchecked")
    public void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            personajes = (List<Personaje>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    // Añade un personaje validando que no exista previamente por su nombre
    public boolean agregarPersonaje(Personaje nuevo) {
        for (Personaje p : personajes) {
            if (p.getNombre().equalsIgnoreCase(nuevo.getNombre())) {
                System.out.println("Error: Ya existe un personaje con ese nombre en el gestor.");
                return false;
            }
        }
        personajes.add(nuevo);
        guardarEnArchivo();
        System.out.println("Personaje agregado con éxito.");
        return true;
    }

    // Muestra todos los personajes registrados
    public void mostrarPersonajes() {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes registrados.");
            return;
        }
        System.out.println("\n--- LISTA DE PERSONAJES ---");
        for (Personaje p : personajes) {
            System.out.println(p);
        }
    }

    // Modifica un personaje existente buscándolo por su nombre
    public boolean modificarPersonaje(String nombre, int vida, int ataque, int defensa, int alcance) {
        for (Personaje p : personajes) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                try {
                    p.setVida(vida);
                    p.setAtaque(ataque);
                    p.setDefensa(defensa);
                    p.setAlcance(alcance);
                    guardarEnArchivo();
                    System.out.println("Personaje modificado con éxito.");
                    return true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error en los valores: " + e.getMessage());
                    return false;
                }
            }
        }
        System.out.println("Personaje no encontrado.");
        return false;
    }

    // Borra un personaje a partir de su nombre
    public boolean borrarPersonaje(String nombre) {
        boolean eliminado = personajes.removeIf(p -> p.getNombre().equalsIgnoreCase(nombre));
        if (eliminado) {
            guardarEnArchivo();
            System.out.println("Personaje eliminado con éxito.");
        } else {
            System.out.println("Personaje no encontrado.");
        }
        return eliminado;
    }

    // =========================================================================
    // NUEVAS FUNCIONALIDADES (Ejercicio 2 de la guía)
    // =========================================================================

    // 1. Actualizar un atributo individual específico
    public boolean actualizarAtributoIndividual(String nombre, String atributo, int valor) {
        for (Personaje p : personajes) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                try {
                    switch (atributo.toLowerCase()) {
                        case "vida":
                            p.setVida(valor);
                            break;
                        case "ataque":
                            p.setAtaque(valor);
                            break;
                        case "defensa":
                            p.setDefensa(valor);
                            break;
                        case "alcance":
                            p.setAlcance(valor);
                            break;
                        default:
                            System.out.println("Atributo no válido.");
                            return false;
                    }
                    guardarEnArchivo();
                    System.out.println("Atributo actualizado con éxito.");
                    return true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return false;
                }
            }
        }
        System.out.println("Personaje no encontrado.");
        return false;
    }

    // 2. Mostrar estadísticas generales (total de personajes y promedios)
    public void mostrarEstadisticas() {
        if (personajes.isEmpty()) {
            System.out.println("No hay estadísticas disponibles.");
            return;
        }
        int total = personajes.size();
        double sumaVida = 0, sumaAtaque = 0, sumaDefensa = 0, sumaAlcance = 0;

        for (Personaje p : personajes) {
            sumaVida += p.getVida();
            sumaAtaque += p.getAtaque();
            sumaDefensa += p.getDefensa();
            sumaAlcance += p.getAlcance();
        }

        System.out.println("\n--- ESTADÍSTICAS GENERALES ---");
        System.out.println("Total de personajes: " + total);
        System.out.println("Promedio de Vida: " + (sumaVida / total));
        System.out.println("Promedio de Ataque: " + (sumaAtaque / total));
        System.out.println("Promedio de Defensa: " + (sumaDefensa / total));
        System.out.println("Promedio de Alcance: " + (sumaAlcance / total));
    }
} package ejercicios;

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
            System.out.println("5. [NUEVO] Actualizar atributo individual");
            System.out.println("6. [NUEVO] Mostrar estadísticas generales");
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
                case 5:
                    System.out.print("Ingrese el nombre del personaje: ");
                    String nombreAt = scanner.nextLine();
                    System.out.print("Ingrese el atributo a actualizar (vida, ataque, defensa, alcance): ");
                    String atributo = scanner.nextLine();
                    System.out.print("Ingrese el nuevo valor (>0): ");
                    int valor = scanner.nextInt();
                    scanner.nextLine();
                    gestor.actualizarAtributoIndividual(nombreAt, atributo, valor);
                    break;
                case 6:
                    gestor.mostrarEstadisticas();
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

A

