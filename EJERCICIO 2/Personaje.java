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


