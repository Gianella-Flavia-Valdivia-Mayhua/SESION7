package Laboratorio07;

public class Ej04_Actividad04 {

    public static void main(String[] args) {

        Ej04_CanalStreamingModelo modelo =
                new Ej04_CanalStreamingModelo();

        Ej04_CanalStreamingVista vista =
                new Ej04_CanalStreamingVista();

        Ej04_CanalStreamingControlador controlador =
                new Ej04_CanalStreamingControlador(
                    modelo,
                    vista
                );

        controlador.iniciar();
    }
}
