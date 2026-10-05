package view;

import vista.jugadores.PanelJugadores;
import vista.jugadores.TarjetaJugador;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Prueba visual conjunta de la pantalla de partida.
 *
 * Objetivo:
 * - integrar GUITablero;
 * - integrar PanelJugadores de Ramsés;
 * - simular dos jugadores;
 * - cambiar visualmente el turno;
 * - comprobar que ambas vistas pueden actualizarse
 *   sin utilizar objetos del dominio.
 *
 * IMPORTANTE:
 * Esta clase es únicamente una prueba gráfica.
 * No contiene reglas reales de Timbiriche.
 */
public class PruebaVistaPartida {

    /*
     * Índice del jugador que visualmente tiene el turno.
     *
     * 0 = Christian
     * 1 = Ramsés
     */
    private static int indiceTurno = 0;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            /*
             * -------------------------------------------------
             * VENTANA PRINCIPAL DE PRUEBA
             * -------------------------------------------------
             */

            JFrame ventana =
                    new JFrame(
                            "Prueba Vista Partida"
                    );

            ventana.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );

            ventana.setLayout(
                    new BorderLayout(
                            10,
                            10
                    )
            );

            /*
             * -------------------------------------------------
             * TABLERO
             * -------------------------------------------------
             */

            GUITablero tablero =
                    new GUITablero();

            tablero.setPuntosPorLado(
                    10
            );

            /*
             * Algunas líneas de ejemplo para mostrar
             * visualmente una partida parcialmente avanzada.
             *
             * No se valida ninguna regla.
             */

            // Líneas azules.
            tablero.pintarLinea(
                    0,
                    0,
                    0,
                    1,
                    Color.BLUE.getRGB()
            );

            tablero.pintarLinea(
                    0,
                    1,
                    1,
                    1,
                    Color.BLUE.getRGB()
            );

            tablero.pintarLinea(
                    1,
                    0,
                    1,
                    1,
                    Color.BLUE.getRGB()
            );

            // Líneas rojas.
            tablero.pintarLinea(
                    2,
                    2,
                    2,
                    3,
                    Color.RED.getRGB()
            );

            tablero.pintarLinea(
                    2,
                    3,
                    3,
                    3,
                    Color.RED.getRGB()
            );

            tablero.pintarLinea(
                    3,
                    2,
                    3,
                    3,
                    Color.RED.getRGB()
            );

            /*
             * Recuadros visuales de ejemplo.
             */

            tablero.pintarRecuadro(
                    0,
                    0,
                    Color.BLUE.getRGB()
            );

            tablero.pintarRecuadro(
                    2,
                    2,
                    Color.RED.getRGB()
            );

            /*
             * El primer turno comienza en azul.
             */
            tablero.setColorTurno(
                    Color.BLUE.getRGB()
            );

            /*
             * -------------------------------------------------
             * PANEL DE JUGADORES
             * -------------------------------------------------
             */

            PanelJugadores panelJugadores =
                    new PanelJugadores();

            /*
             * Las tarjetas utilizan datos simples de prueba.
             *
             * Constructor real de Ramsés:
             *
             * TarjetaJugador(
             *      String nombre,
             *      Color color,
             *      Icon iconAvatar,
             *      int cuadros
             * )
             */

            TarjetaJugador jugadorChristian =
                    new TarjetaJugador(
                            "Christian",
                            Color.BLUE,
                            null,
                            1
                    );

            TarjetaJugador jugadorRamses =
                    new TarjetaJugador(
                            "Ramsés",
                            Color.RED,
                            null,
                            1
                    );

            List<TarjetaJugador> jugadores =
                    new ArrayList<>();

            jugadores.add(
                    jugadorChristian
            );

            jugadores.add(
                    jugadorRamses
            );

            /*
             * Firma real:
             *
             * setJugadores(List<TarjetaJugador>)
             */
            panelJugadores.setJugadores(
                    jugadores
            );

            /*
             * Firma real:
             *
             * setTurnoJugador(int indice)
             */
            panelJugadores.setTurnoJugador(
                    indiceTurno
            );

            /*
             * -------------------------------------------------
             * PANEL DE CONTROLES DE PRUEBA
             * -------------------------------------------------
             */

            JPanel panelControles =
                    new JPanel(
                            new FlowLayout(
                                    FlowLayout.CENTER
                            )
                    );

            JButton botonCambiarTurno =
                    new JButton(
                            "Cambiar turno"
                    );

            panelControles.add(
                    botonCambiarTurno
            );

            /*
             * El botón NO ejecuta reglas de negocio.
             *
             * Solo intercambia visualmente quién aparece
             * como jugador en turno.
             */
            botonCambiarTurno.addActionListener(
                    e -> {

                        /*
                         * Alterna:
                         *
                         * 0 -> 1
                         * 1 -> 0
                         */
                        indiceTurno =
                                indiceTurno == 0
                                        ? 1
                                        : 0;

                        /*
                         * Actualizamos el PanelJugadores.
                         */
                        panelJugadores.setTurnoJugador(
                                indiceTurno
                        );

                        /*
                         * Actualizamos el marco visual
                         * del tablero.
                         */
                        if (indiceTurno == 0) {

                            tablero.setColorTurno(
                                    Color.BLUE.getRGB()
                            );

                        } else {

                            tablero.setColorTurno(
                                    Color.RED.getRGB()
                            );
                        }
                    }
            );

            /*
             * -------------------------------------------------
             * COMPOSICIÓN DE LA VENTANA
             * -------------------------------------------------
             */

            ventana.add(
                    panelJugadores,
                    BorderLayout.NORTH
            );

            ventana.add(
                    tablero,
                    BorderLayout.CENTER
            );

            ventana.add(
                    panelControles,
                    BorderLayout.SOUTH
            );

            /*
             * Ajustar ventana al contenido.
             */
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