package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista gráfica del tablero de Timbiriche.
 *
 * Esta clase se limita a representar información visual.
 *
 * No contiene reglas de negocio.
 * No conoce objetos del dominio como:
 *
 * - Partida
 * - Jugador
 * - Punto
 * - Linea
 * - Recuadro
 * - Jugada
 *
 * La información necesaria para pintar se recibe mediante
 * tipos simples: int, arreglos de int y valores RGB.
 *
 * Convención de coordenadas:
 *
 * fila    -> eje Y
 * columna -> eje X
 *
 * Para colores se utiliza un entero RGB.
 *
 * Ejemplo:
 *
 * Color.RED.getRGB()
 */
public class GUITablero extends JPanel {

    /*
     * ---------------------------------------------------------
     * CONFIGURACIÓN VISUAL
     * ---------------------------------------------------------
     */

    private static final int MARGEN = 25;

    private static final int ANCHO_PREFERIDO = 420;
    private static final int ALTO_PREFERIDO = 420;

    /*
     * Cantidad de puntos por lado.
     *
     * Este valor puede cambiar mediante setPuntosPorLado().
     */
    private int puntosPorLado = 10;

    /*
     * ---------------------------------------------------------
     * ESTADO VISUAL
     * ---------------------------------------------------------
     */

    /*
     * Cada línea se almacena de la siguiente manera:
     *
     * [0] filaInicio
     * [1] columnaInicio
     * [2] filaFin
     * [3] columnaFin
     * [4] colorRGB
     */
    private final List<int[]> lineas =
            new ArrayList<>();

    /*
     * Cada recuadro se almacena como:
     *
     * [0] fila
     * [1] columna
     * [2] colorRGB
     *
     * fila y columna representan la esquina
     * superior izquierda del recuadro.
     */
    private final List<int[]> recuadros =
            new ArrayList<>();

    /*
     * Punto seleccionado únicamente para representación.
     *
     * -1 indica que no existe selección.
     */
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;

    /*
     * Candidatos visuales.
     *
     * Cada posición tiene:
     *
     * [0] fila
     * [1] columna
     *
     * GUITablero NO calcula estos candidatos.
     * Solamente los recibe y los pinta.
     */
    private int[][] candidatos =
            new int[0][0];

    /*
     * Color utilizado para representar el turno actual
     * mediante el marco del tablero.
     */
    private int colorTurnoRGB =
            Color.DARK_GRAY.getRGB();

    /*
     * Permite activar o desactivar visualmente
     * el marco del turno.
     */
    private boolean mostrarMarcoTurno = false;

    /*
     * ---------------------------------------------------------
     * CONSTRUCTOR
     * ---------------------------------------------------------
     */

    public GUITablero() {

        setPreferredSize(
                new Dimension(
                        ANCHO_PREFERIDO,
                        ALTO_PREFERIDO
                )
        );

        setBackground(
                Color.WHITE
        );
    }

    /*
     * =========================================================
     * API PÚBLICA DE LA VISTA
     * =========================================================
     */

    /**
     * Define cuántos puntos tendrá el tablero por lado.
     *
     * Ejemplo:
     *
     * setPuntosPorLado(10);
     *
     * genera una cuadrícula visual de 10 x 10 puntos.
     */
    public void setPuntosPorLado(
            int puntosPorLado) {

        /*
         * Para poder formar al menos una línea
         * necesitamos dos puntos.
         */
        if (puntosPorLado < 2) {
            return;
        }

        this.puntosPorLado =
                puntosPorLado;

        repaint();
    }

    /**
     * Agrega una línea a la representación gráfica.
     *
     * GUITablero no comprueba si la jugada es válida.
     * Simplemente pinta las coordenadas recibidas.
     */
    public void pintarLinea(
            int filaInicio,
            int columnaInicio,
            int filaFin,
            int columnaFin,
            int colorRGB) {

        lineas.add(
                new int[]{
                        filaInicio,
                        columnaInicio,
                        filaFin,
                        columnaFin,
                        colorRGB
                }
        );

        repaint();
    }

    /**
     * Pinta un recuadro.
     *
     * fila y columna indican la celda ubicada entre:
     *
     * (fila, columna)
     *
     * y
     *
     * (fila + 1, columna + 1)
     *
     * La vista solamente representa el dato.
     * No determina quién cerró el recuadro.
     */
    public void pintarRecuadro(
            int fila,
            int columna,
            int colorRGB) {

        recuadros.add(
                new int[]{
                        fila,
                        columna,
                        colorRGB
                }
        );

        repaint();
    }

    /**
     * Indica visualmente un punto seleccionado.
     *
     * GUITablero no determina si el punto puede
     * seleccionarse o no.
     */
    public void pintarSeleccion(
            int fila,
            int columna) {

        filaSeleccionada =
                fila;

        columnaSeleccionada =
                columna;

        repaint();
    }

    /**
     * Elimina únicamente el marcador visual
     * correspondiente al punto seleccionado.
     */
    public void limpiarSeleccion() {

        filaSeleccionada =
                -1;

        columnaSeleccionada =
                -1;

        repaint();
    }

    /**
     * Recibe las posiciones que deben mostrarse
     * como candidatos.
     *
     * Formato esperado:
     *
     * {
     *     {fila, columna},
     *     {fila, columna},
     *     ...
     * }
     *
     * GUITablero no calcula estos valores.
     */
    public void pintarCandidatos(
            int[][] candidatos) {

        if (candidatos == null) {

            this.candidatos =
                    new int[0][0];

        } else {

            this.candidatos =
                    candidatos;
        }

        repaint();
    }

    /**
     * Elimina los marcadores visuales de candidatos.
     */
    public void limpiarCandidatos() {

        candidatos =
                new int[0][0];

        repaint();
    }

    /**
     * Define el color correspondiente al turno actual.
     *
     * El color se representa mediante un entero RGB.
     */
    public void setColorTurno(
            int colorRGB) {

        colorTurnoRGB =
                colorRGB;

        mostrarMarcoTurno =
                true;

        repaint();
    }

    /**
     * Oculta el marco que representa al jugador
     * actualmente en turno.
     */
    public void ocultarColorTurno() {

        mostrarMarcoTurno =
                false;

        repaint();
    }

    /**
     * Limpia todo el estado visual del tablero.
     *
     * No cambia la cantidad de puntos por lado.
     */
    public void limpiarTablero() {

        lineas.clear();

        recuadros.clear();

        filaSeleccionada =
                -1;

        columnaSeleccionada =
                -1;

        candidatos =
                new int[0][0];

        mostrarMarcoTurno =
                false;

        repaint();
    }

    /*
     * =========================================================
     * GEOMETRÍA
     * =========================================================
     */

    /**
     * Calcula la separación entre los puntos.
     *
     * El tablero conserva proporciones cuadradas
     * independientemente del tamaño del JPanel.
     */
    private double calcularSeparacion() {

        int ladoDisponible =
                Math.min(
                        getWidth(),
                        getHeight()
                );

        int espacioUtil =
                ladoDisponible
                        - (MARGEN * 2);

        if (espacioUtil <= 0) {
            return 1.0;
        }

        return (double) espacioUtil
                / (puntosPorLado - 1);
    }

    /**
     * Convierte una columna lógica
     * en una coordenada X.
     */
    private int xDeColumna(
            int columna) {

        return (int) Math.round(
                MARGEN
                        + columna
                        * calcularSeparacion()
        );
    }

    /**
     * Convierte una fila lógica
     * en una coordenada Y.
     */
    private int yDeFila(
            int fila) {

        return (int) Math.round(
                MARGEN
                        + fila
                        * calcularSeparacion()
        );
    }

    /**
     * Calcula el radio utilizado para
     * representar cada punto.
     */
    private int calcularRadioPunto() {

        int radio =
                (int) Math.floor(
                        calcularSeparacion()
                                / 4.0
                );

        return Math.max(
                2,
                Math.min(
                        5,
                        radio
                )
        );
    }

    /**
     * Radio utilizado para la selección.
     */
    private int calcularRadioSeleccion() {

        int radio =
                (int) Math.floor(
                        calcularSeparacion()
                                * 0.10
                );

        return Math.max(
                6,
                Math.min(
                        10,
                        radio
                )
        );
    }

    /**
     * Radio utilizado para candidatos.
     */
    private int calcularRadioCandidato() {

        return calcularRadioPunto()
                + 4;
    }

    /*
     * =========================================================
     * PINTADO
     * =========================================================
     */

    /**
     * Método principal de Swing para pintar el tablero.
     */
    @Override
    protected void paintComponent(
            Graphics g) {

        super.paintComponent(
                g
        );

        Graphics2D g2 =
                (Graphics2D) g.create();

        /*
         * Suaviza líneas y figuras.
         */
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        /*
         * Orden de pintado:
         *
         * 1. recuadros
         * 2. líneas
         * 3. puntos
         * 4. candidatos
         * 5. selección
         * 6. marco del turno
         *
         * El orden evita que los recuadros oculten
         * las líneas o los puntos.
         */
        dibujarRecuadros(
                g2
        );

        dibujarLineas(
                g2
        );

        dibujarPuntos(
                g2
        );

        dibujarCandidatos(
                g2
        );

        dibujarSeleccion(
                g2
        );

        dibujarMarcoTurno(
                g2
        );

        g2.dispose();
    }

    /**
     * Dibuja los puntos que conforman el tablero.
     */
    private void dibujarPuntos(
            Graphics2D g2) {

        Color colorAnterior =
                g2.getColor();

        g2.setColor(
                Color.BLACK
        );

        int radio =
                calcularRadioPunto();

        for (int fila = 0;
             fila < puntosPorLado;
             fila++) {

            for (int columna = 0;
                 columna < puntosPorLado;
                 columna++) {

                int x =
                        xDeColumna(
                                columna
                        );

                int y =
                        yDeFila(
                                fila
                        );

                g2.fillOval(
                        x - radio,
                        y - radio,
                        radio * 2,
                        radio * 2
                );
            }
        }

        g2.setColor(
                colorAnterior
        );
    }

    /**
     * Dibuja todas las líneas previamente
     * indicadas mediante pintarLinea().
     */
    private void dibujarLineas(
            Graphics2D g2) {

        Stroke strokeAnterior =
                g2.getStroke();

        Color colorAnterior =
                g2.getColor();

        g2.setStroke(
                new BasicStroke(
                        4.0f,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND
                )
        );

        for (int[] linea :
                lineas) {

            /*
             * Protección por si accidentalmente
             * se recibe un arreglo incompleto.
             */
            if (linea == null
                    || linea.length < 5) {

                continue;
            }

            int filaInicio =
                    linea[0];

            int columnaInicio =
                    linea[1];

            int filaFin =
                    linea[2];

            int columnaFin =
                    linea[3];

            int colorRGB =
                    linea[4];

            g2.setColor(
                    new Color(
                            colorRGB,
                            true
                    )
            );

            g2.drawLine(
                    xDeColumna(
                            columnaInicio
                    ),
                    yDeFila(
                            filaInicio
                    ),
                    xDeColumna(
                            columnaFin
                    ),
                    yDeFila(
                            filaFin
                    )
            );
        }

        g2.setStroke(
                strokeAnterior
        );

        g2.setColor(
                colorAnterior
        );
    }

    /**
     * Dibuja los recuadros previamente indicados
     * mediante pintarRecuadro().
     */
    private void dibujarRecuadros(
            Graphics2D g2) {

        Color colorAnterior =
                g2.getColor();

        for (int[] recuadro :
                recuadros) {

            if (recuadro == null
                    || recuadro.length < 3) {

                continue;
            }

            int fila =
                    recuadro[0];

            int columna =
                    recuadro[1];

            int colorRGB =
                    recuadro[2];

            /*
             * Una celda requiere existir entre
             * dos filas y dos columnas de puntos.
             */
            if (fila < 0
                    || columna < 0
                    || fila >= puntosPorLado - 1
                    || columna >= puntosPorLado - 1) {

                continue;
            }

            int x1 =
                    xDeColumna(
                            columna
                    );

            int y1 =
                    yDeFila(
                            fila
                    );

            int x2 =
                    xDeColumna(
                            columna + 1
                    );

            int y2 =
                    yDeFila(
                            fila + 1
                    );

            /*
             * Usamos transparencia para conservar
             * visibles puntos y líneas.
             */
            Color colorBase =
                    new Color(
                            colorRGB,
                            true
                    );

            Color colorRecuadro =
                    new Color(
                            colorBase.getRed(),
                            colorBase.getGreen(),
                            colorBase.getBlue(),
                            80
                    );

            g2.setColor(
                    colorRecuadro
            );

            g2.fillRect(
                    x1,
                    y1,
                    x2 - x1,
                    y2 - y1
            );
        }

        g2.setColor(
                colorAnterior
        );
    }

    /**
     * Dibuja el punto actualmente seleccionado.
     */
    private void dibujarSeleccion(
            Graphics2D g2) {

        if (filaSeleccionada < 0
                || columnaSeleccionada < 0) {

            return;
        }

        int x =
                xDeColumna(
                        columnaSeleccionada
                );

        int y =
                yDeFila(
                        filaSeleccionada
                );

        int radio =
                calcularRadioSeleccion();

        Color colorAnterior =
                g2.getColor();

        Stroke strokeAnterior =
                g2.getStroke();

        g2.setColor(
                Color.BLACK
        );

        g2.setStroke(
                new BasicStroke(
                        2.0f
                )
        );

        g2.drawOval(
                x - radio,
                y - radio,
                radio * 2,
                radio * 2
        );

        g2.setStroke(
                strokeAnterior
        );

        g2.setColor(
                colorAnterior
        );
    }

    /**
     * Representa visualmente los candidatos recibidos.
     *
     * Esta clase no determina cuáles son candidatos.
     */
    private void dibujarCandidatos(
            Graphics2D g2) {

        if (candidatos == null) {
            return;
        }

        Color colorAnterior =
                g2.getColor();

        Stroke strokeAnterior =
                g2.getStroke();

        g2.setColor(
                Color.GRAY
        );

        g2.setStroke(
                new BasicStroke(
                        1.5f
                )
        );

        int radio =
                calcularRadioCandidato();

        for (int[] candidato :
                candidatos) {

            if (candidato == null
                    || candidato.length < 2) {

                continue;
            }

            int fila =
                    candidato[0];

            int columna =
                    candidato[1];

            int x =
                    xDeColumna(
                            columna
                    );

            int y =
                    yDeFila(
                            fila
                    );

            g2.drawOval(
                    x - radio,
                    y - radio,
                    radio * 2,
                    radio * 2
            );
        }

        g2.setStroke(
                strokeAnterior
        );

        g2.setColor(
                colorAnterior
        );
    }

    /**
     * Dibuja un marco alrededor del tablero
     * utilizando el color correspondiente al turno.
     */
    private void dibujarMarcoTurno(
            Graphics2D g2) {

        if (!mostrarMarcoTurno) {
            return;
        }

        int xInicio =
                xDeColumna(
                        0
                );

        int yInicio =
                yDeFila(
                        0
                );

        int xFin =
                xDeColumna(
                        puntosPorLado - 1
                );

        int yFin =
                yDeFila(
                        puntosPorLado - 1
                );

        /*
         * Dejamos el marco ligeramente separado
         * de los puntos exteriores.
         */
        int separacionMarco =
                10;

        Color colorAnterior =
                g2.getColor();

        Stroke strokeAnterior =
                g2.getStroke();

        g2.setColor(
                new Color(
                        colorTurnoRGB,
                        true
                )
        );

        g2.setStroke(
                new BasicStroke(
                        4.0f
                )
        );

        g2.drawRect(
                xInicio - separacionMarco,
                yInicio - separacionMarco,
                (xFin - xInicio)
                        + (separacionMarco * 2),
                (yFin - yInicio)
                        + (separacionMarco * 2)
        );

        g2.setStroke(
                strokeAnterior
        );

        g2.setColor(
                colorAnterior
        );
    }
}