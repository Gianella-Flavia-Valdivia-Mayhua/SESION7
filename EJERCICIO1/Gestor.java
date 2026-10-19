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
}
