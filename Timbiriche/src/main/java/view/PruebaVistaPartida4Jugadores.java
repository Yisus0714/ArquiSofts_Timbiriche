package view;

import vista.jugadores.PanelJugadores;
import vista.jugadores.TarjetaJugador;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Prueba visual de una partida con cuatro jugadores.
 *
 * Objetivos:
 *
 * - Mostrar los cuatro jugadores en la parte superior.
 * - Mostrar un único tablero compartido.
 * - Simular visualmente el cambio de turno.
 * - Cambiar el color del marco del tablero según
 *   el jugador que tiene el turno.
 *
 * IMPORTANTE:
 *
 * Esta clase solamente sirve como prueba gráfica.
 * No contiene reglas reales de Timbiriche.
 * No utiliza objetos del dominio.
 */
public class PruebaVistaPartida4Jugadores {

    /*
     * Índice visual del jugador actualmente en turno.
     *
     * 0 = Christian
     * 1 = Ramsés
     * 2 = Jesús
     * 3 = Miguel
     */
    private static int indiceTurno = 0;

    /*
     * Colores utilizados únicamente para esta simulación.
     */
    private static final Color COLOR_CHRISTIAN =
            Color.BLUE;

    private static final Color COLOR_RAMSES =
            Color.RED;

    private static final Color COLOR_JESUS =
            Color.GREEN;

    private static final Color COLOR_MIGUEL =
            Color.MAGENTA;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            /*
             * -------------------------------------------------
             * VENTANA PRINCIPAL
             * -------------------------------------------------
             */
            JFrame ventana =
                    new JFrame(
                            "Prueba Vista Partida - 4 Jugadores"
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
             * PANEL DE JUGADORES
             * -------------------------------------------------
             */
            PanelJugadores panelJugadores =
                    new PanelJugadores();

            /*
             * El primer jugador representa al usuario local.
             * Por diseño visual no se muestra su nombre,
             * sino simplemente "TU".
             */
            TarjetaJugador christian =
                    new TarjetaJugador(
                            "TU",
                            COLOR_CHRISTIAN,
                            null,
                            1
                    );

            TarjetaJugador ramses =
                    new TarjetaJugador(
                            "Ramsés",
                            COLOR_RAMSES,
                            null,
                            1
                    );

            TarjetaJugador jesus =
                    new TarjetaJugador(
                            "Jesús",
                            COLOR_JESUS,
                            null,
                            1
                    );

            TarjetaJugador miguel =
                    new TarjetaJugador(
                            "Miguel",
                            COLOR_MIGUEL,
                            null,
                            1
                    );

            /*
             * PanelJugadores recibe las tarjetas
             * que debe mostrar.
             */
            List<TarjetaJugador> jugadores =
                    new ArrayList<>();

            jugadores.add(
                    christian
            );

            jugadores.add(
                    ramses
            );

            jugadores.add(
                    jesus
            );

            jugadores.add(
                    miguel
            );

            panelJugadores.setJugadores(
                    jugadores
            );

            /*
             * Christian inicia visualmente con el turno.
             */
            panelJugadores.setTurnoJugador(
                    indiceTurno
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
             * -------------------------------------------------
             * SIMULACIÓN VISUAL DE JUGADAS
             * -------------------------------------------------
             *
             * Estas líneas y cuadros NO representan
             * lógica real de una partida.
             *
             * Solo permiten comprobar el pintado.
             */

            /*
             * Christian - azul.
             */
            tablero.pintarLinea(
                    0,
                    0,
                    0,
                    1,
                    COLOR_CHRISTIAN.getRGB()
            );

            tablero.pintarLinea(
                    0,
                    1,
                    1,
                    1,
                    COLOR_CHRISTIAN.getRGB()
            );

            tablero.pintarLinea(
                    1,
                    0,
                    1,
                    1,
                    COLOR_CHRISTIAN.getRGB()
            );

            tablero.pintarRecuadro(
                    0,
                    0,
                    COLOR_CHRISTIAN.getRGB()
            );

            /*
             * Ramsés - rojo.
             */
            tablero.pintarLinea(
                    2,
                    2,
                    2,
                    3,
                    COLOR_RAMSES.getRGB()
            );

            tablero.pintarLinea(
                    2,
                    3,
                    3,
                    3,
                    COLOR_RAMSES.getRGB()
            );

            tablero.pintarLinea(
                    3,
                    2,
                    3,
                    3,
                    COLOR_RAMSES.getRGB()
            );

            tablero.pintarRecuadro(
                    2,
                    2,
                    COLOR_RAMSES.getRGB()
            );

            /*
             * Jesús - verde.
             */
            tablero.pintarLinea(
                    5,
                    5,
                    5,
                    6,
                    COLOR_JESUS.getRGB()
            );

            tablero.pintarLinea(
                    5,
                    6,
                    6,
                    6,
                    COLOR_JESUS.getRGB()
            );

            tablero.pintarLinea(
                    6,
                    5,
                    6,
                    6,
                    COLOR_JESUS.getRGB()
            );

            tablero.pintarRecuadro(
                    5,
                    5,
                    COLOR_JESUS.getRGB()
            );

            /*
             * Miguel - magenta.
             */
            tablero.pintarLinea(
                    7,
                    1,
                    8,
                    1,
                    COLOR_MIGUEL.getRGB()
            );

            tablero.pintarLinea(
                    8,
                    1,
                    8,
                    2,
                    COLOR_MIGUEL.getRGB()
            );

            tablero.pintarLinea(
                    7,
                    2,
                    8,
                    2,
                    COLOR_MIGUEL.getRGB()
            );

            tablero.pintarRecuadro(
                    7,
                    1,
                    COLOR_MIGUEL.getRGB()
            );

            /*
             * El marco inicia utilizando el color
             * del jugador que tiene el turno.
             */
            tablero.setColorTurno(
                    COLOR_CHRISTIAN.getRGB()
            );

            /*
             * -------------------------------------------------
             * CONTENEDOR DEL TABLERO
             * -------------------------------------------------
             *
             * GridBagLayout se usa únicamente para mantener
             * el tablero centrado y respetar su tamaño preferido.
             */
            JPanel contenedorTablero =
                    new JPanel(
                            new GridBagLayout()
                    );

            contenedorTablero.add(
                    tablero
            );

            /*
             * -------------------------------------------------
             * BOTÓN PARA CAMBIAR TURNO
             * -------------------------------------------------
             */
            JButton botonCambiarTurno =
                    new JButton(
                            "Cambiar turno"
                    );

            JPanel panelControles =
                    new JPanel(
                            new FlowLayout(
                                    FlowLayout.CENTER
                            )
                    );

            panelControles.add(
                    botonCambiarTurno
            );

            /*
             * -------------------------------------------------
             * SIMULACIÓN DEL CAMBIO DE TURNO
             * -------------------------------------------------
             *
             * Se utiliza únicamente para comprobar que:
             *
             * - PanelJugadores cambia "SU TURNO".
             * - GUITablero cambia el color del marco.
             *
             * No representa todavía la lógica real
             * de cambio de turno de la partida.
             */
            botonCambiarTurno.addActionListener(
                    e -> {

                        /*
                         * Avanzamos al siguiente jugador.
                         *
                         * 0 -> 1
                         * 1 -> 2
                         * 2 -> 3
                         * 3 -> 0
                         */
                        indiceTurno =
                                (indiceTurno + 1) % 4;

                        /*
                         * PanelJugadores actualiza
                         * visualmente quién tiene el turno.
                         */
                        panelJugadores.setTurnoJugador(
                                indiceTurno
                        );

                        /*
                         * El tablero actualiza el marco
                         * utilizando el mismo jugador.
                         */
                        switch (indiceTurno) {

                            case 0 ->
                                    tablero.setColorTurno(
                                            COLOR_CHRISTIAN.getRGB()
                                    );

                            case 1 ->
                                    tablero.setColorTurno(
                                            COLOR_RAMSES.getRGB()
                                    );

                            case 2 ->
                                    tablero.setColorTurno(
                                            COLOR_JESUS.getRGB()
                                    );

                            case 3 ->
                                    tablero.setColorTurno(
                                            COLOR_MIGUEL.getRGB()
                                    );
                        }
                    }
            );

            /*
             * -------------------------------------------------
             * COMPOSICIÓN FINAL
             * -------------------------------------------------
             *
             * Los cuatro jugadores quedan en la parte superior,
             * tal como se definió en el storyboard.
             *
             * El tablero queda centrado.
             *
             * El botón se coloca abajo únicamente
             * como herramienta de prueba.
             */
            ventana.add(
                    panelJugadores,
                    BorderLayout.NORTH
            );

            ventana.add(
                    contenedorTablero,
                    BorderLayout.CENTER
            );

            ventana.add(
                    panelControles,
                    BorderLayout.SOUTH
            );

            /*
             * Espacio alrededor de la ventana.
             */
            ((JComponent) ventana.getContentPane())
                    .setBorder(
                            BorderFactory.createEmptyBorder(
                                    10,
                                    10,
                                    10,
                                    10
                            )
                    );

            /*
             * -------------------------------------------------
             * MOSTRAR VENTANA
             * -------------------------------------------------
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