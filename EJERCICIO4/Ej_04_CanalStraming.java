package Laboratorio07;

public class Ej04_CanalStreaming {

    private int id;
    private String nombre;
    private double precioSuscripcion;

    // Constructor
    public Ej04_CanalStreaming(
            int id, String nombre, double precioSuscripcion) {

        setId(id);
        setNombre(nombre);
        setPrecioSuscripcion(precioSuscripcion);
    }

    // Obtener identificador
    public int getId() {
        return id;
    }

    // Modificar identificador
    public void setId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "El ID debe ser mayor que cero."
            );
        }

        this.id = id;
    }

    // Obtener nombre
    public String getNombre() {
        return nombre;
    }

    // Modificar nombre
    public void setNombre(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El nombre no puede estar vacio."
            );
        }

        if (nombre.contains("|")) {
            throw new IllegalArgumentException(
                "El nombre no puede contener el simbolo |."
            );
        }

        this.nombre = nombre.trim();
    }

    // Obtener precio
    public double getPrecioSuscripcion() {
        return precioSuscripcion;
    }

    // Modificar precio
    public void setPrecioSuscripcion(double precioSuscripcion) {

        if (!Double.isFinite(precioSuscripcion)
                || precioSuscripcion < 0) {

            throw new IllegalArgumentException(
                "El precio debe ser un numero valido y no negativo."
            );
        }

        this.precioSuscripcion = precioSuscripcion;
    }

    // Mostrar informacion del canal
    @Override
    public String toString() {

        return "ID: " + id
                + " | Canal: " + nombre
                + " | Precio de suscripcion: S/. "
                + String.format("%.2f", precioSuscripcion);
    }
}
