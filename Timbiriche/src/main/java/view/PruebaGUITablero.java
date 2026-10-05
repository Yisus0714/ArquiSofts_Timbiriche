package view;

import javax.swing.*;
import java.awt.*;

public class PruebaGUITablero {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame ventana =
                    new JFrame(
                            "Prueba GUITablero"
                    );

            GUITablero tablero =
                    new GUITablero();

            /*
             * -------------------------------------------------
             * PRUEBAS UNITARIAS VISUALES
             * -------------------------------------------------
             */

            /*
             * Color correspondiente al turno.
             */
            tablero.setColorTurno(
                    Color.BLUE.getRGB()
            );

            /*
             * Línea horizontal.
             */
            tablero.pintarLinea(
                    0,
                    0,
                    0,
                    1,
                    Color.BLUE.getRGB()
            );

            /*
             * Línea vertical.
             */
            tablero.pintarLinea(
                    0,
                    1,
                    1,
                    1,
                    Color.RED.getRGB()
            );

            /*
             * Recuadro.
             */
            tablero.pintarRecuadro(
                    0,
                    0,
                    Color.BLUE.getRGB()
            );

            /*
             * Punto seleccionado.
             */
            tablero.pintarSeleccion(
                    3,
                    3
            );

            /*
             * Candidatos entregados a la vista.
             *
             * La vista NO los calcula.
             */
            tablero.pintarCandidatos(
                    new int[][]{
                            {2, 3},
                            {3, 2},
                            {3, 4},
                            {4, 3}
                    }
            );

            ventana.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );

            ventana.add(
                    tablero
            );

            ventana.pack();

            ventana.setLocationRelativeTo(
                    null
            );

            ventana.setVisible(
                    true
            );
        });
    }
}