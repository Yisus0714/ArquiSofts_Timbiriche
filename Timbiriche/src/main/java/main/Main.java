package main;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicación.
 *
 * Su única responsabilidad es iniciar la aplicación y delegar
 * la creación/conexión de los componentes a Ensamblador.
 *
 * Main no debe conocer directamente Vistas, Controladores,
 * Modelos MVC ni elementos del dominio.
 *
 * En UML:
 * - Main tendrá una dependencia hacia Ensamblador.
 */
public class Main {

    /**
     * Inicia la aplicación dentro del hilo de eventos de Swing
     * y delega el armado de los componentes a Ensamblador.
     *
     * En UML:
     * + main(args: String[]): void
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Ensamblador ensamblador =
                    new Ensamblador();

            ensamblador.ensamblar();
        });
    }
}