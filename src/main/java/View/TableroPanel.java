package View;

import Control.ControlEjercerTurno;
import Model.IConsultaTablero;
import dto.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TableroPanel extends JPanel {

    private final IConsultaTablero consultaTablero;
    private final ControlEjercerTurno control;
    /** Lado del tablero en puntos; se lee del Modelo cuando inicia la partida. */
    private int dimension;

    private Color colorMarco = new Color(37, 99, 235);
    private boolean esTurnoLocal = false;

    private SeleccionDTO seleccionActual = null;
    private List<JugadaDTO> jugadasRealizadas = null;

    public TableroPanel(IConsultaTablero consultaTablero, ControlEjercerTurno control) {
        this.consultaTablero = consultaTablero;
        this.control = control;

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 600));
        setMaximumSize(new Dimension(650, 650));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manejarClickTablero(e.getX(), e.getY());
            }
        });

        sincronizarEstado();
    }

    public void sincronizarEstado() {
        try {
            JugadorTableroDTO jugadorTablero = consultaTablero.getJugadorEnTurnoTablero();
            if (jugadorTablero != null) {
                this.esTurnoLocal = jugadorTablero.isEsTu();
                if (jugadorTablero.getColor() != null) {
                    this.colorMarco = jugadorTablero.getColor();
                }
            }
            this.seleccionActual = consultaTablero.getOnSelect();
            this.jugadasRealizadas = consultaTablero.getJugadas();
            this.dimension = consultaTablero.getDimension();
            repaint();
        } catch (Exception e) {
            // Manejo de excepción
        }
    }

    private void manejarClickTablero(int mouseX, int mouseY) {
        if (!esTurnoLocal || dimension < 2) return;

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int boardSize = Math.min(panelWidth, panelHeight) - 80;
        int startX = (panelWidth - boardSize) / 2;
        int startY = (panelHeight - boardSize) / 2;
        double spacing = (double) boardSize / (dimension - 1);
        int radioPunto = 10;

        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c < dimension; c++) {
                int px = (int) (startX + c * spacing);
                int py = (int) (startY + r * spacing);

                // Detectar clic cercano al punto (radio de tolerancia)
                if (Math.hypot(mouseX - px, mouseY - py) <= radioPunto + 6) {
                    // Validar reglas de selección
                    if (esPuntoPermitido(r, c)) {
                        try {
                            control.seleccionarPunto(r, c);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en jugada", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    return;
                }
            }
        }
    }

    private boolean esPuntoPermitido(int fila, int columna) {
        if (seleccionActual == null || seleccionActual.getPuntoSelec() == null) {
            // Regla 1: Si no hay punto seleccionado, se puede hacer clic en cualquier punto
            return true;
        }
        // Regla 2: Volver a hacer clic en el punto seleccionado lo deselecciona
        if (seleccionActual.getPuntoSelec().getFila() == fila && seleccionActual.getPuntoSelec().getColumna() == columna) {
            return true;
        }
        // Regla 3: Si ya hay un punto seleccionado, validar contra puntos disponibles
        List<PuntoDTO> disponibles = seleccionActual.getPuntosDisponibles();
        if (disponibles != null) {
            for (PuntoDTO p : disponibles) {
                if (p.getFila() == fila && p.getColumna() == columna) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int boardSize = Math.min(panelWidth, panelHeight) - 80;
        int startX = (panelWidth - boardSize) / 2;
        int startY = (panelHeight - boardSize) / 2;

        // 1. Dibujar Marco del Tablero con el color del jugador en turno
        g2.setColor(colorMarco);
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawRoundRect(startX - 20, startY - 20, boardSize + 40, boardSize + 40, 24, 24);

        if (dimension < 2) {
            return;                      // aún no hay partida que dibujar
        }
        double spacing = (double) boardSize / (dimension - 1);

        // 2. Dibujar Recuadros Completados (Independientes de las líneas)
        if (jugadasRealizadas != null) {
            for (JugadaDTO jugada : jugadasRealizadas) {
                List<RecuadroDTO> recuadros = jugada.getRecuadrosCompletados();
                if (recuadros != null) {
                    for (RecuadroDTO rec : recuadros) {
                        int rx = (int) (startX + rec.getColumna() * spacing);
                        int ry = (int) (startY + rec.getFila() * spacing);
                        
                        // Pintar recuadro completado
                        g2.setColor(new Color(jugada.getColor().getRed(), jugada.getColor().getGreen(), jugada.getColor().getBlue(), 50));
                        g2.fillRect(rx, ry, (int) spacing, (int) spacing);

                        // Pintar inicial del jugador en mayúscula al centro del recuadro
                        g2.setColor(jugada.getColor());
                        g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(16, (int)(spacing * 0.35))));
                        String inicial = String.valueOf(jugada.getInicialJugador()).toUpperCase();
                        FontMetrics fm = g2.getFontMetrics();
                        int textX = rx + ((int) spacing - fm.stringWidth(inicial)) / 2;
                        int textY = ry + ((int) spacing + fm.getAscent() - fm.getDescent()) / 2;
                        g2.drawString(inicial, textX, textY);
                    }
                }
            }

            // 3. Dibujar Líneas de las jugadas
            for (JugadaDTO jugada : jugadasRealizadas) {
                LineaDTO linea = jugada.getLinea();
                if (linea != null && linea.getInicio() != null && linea.getFin() != null) {
                    int x1 = (int) (startX + linea.getInicio().getColumna() * spacing);
                    int y1 = (int) (startY + linea.getInicio().getFila() * spacing);
                    int x2 = (int) (startX + linea.getFin().getColumna() * spacing);
                    int y2 = (int) (startY + linea.getFin().getFila() * spacing);

                    g2.setColor(jugada.getColor());
                    g2.setStroke(new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(x1, y1, x2, y2);
                }
            }
        }

        // 4. Dibujar Puntos de la cuadrícula y efectos visuales de selección
        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c < dimension; c++) {
                int px = (int) (startX + c * spacing);
                int py = (int) (startY + r * spacing);

                boolean esSeleccionado = false;
                boolean esDisponible = false;
                Color colorJugadorActual = colorMarco;

                if (seleccionActual != null) {
                    if (seleccionActual.getPuntoSelec() != null &&
                        seleccionActual.getPuntoSelec().getFila() == r &&
                        seleccionActual.getPuntoSelec().getColumna() == c) {
                        esSeleccionado = true;
                        if (seleccionActual.getColor() != null) colorJugadorActual = seleccionActual.getColor();
                    } else {
                        List<PuntoDTO> disponibles = seleccionActual.getPuntosDisponibles();
                        if (disponibles != null) {
                            for (PuntoDTO p : disponibles) {
                                if (p.getFila() == r && p.getColumna() == c) {
                                    esDisponible = true;
                                    if (seleccionActual.getColor() != null) colorJugadorActual = seleccionActual.getColor();
                                    break;
                                }
                            }
                        }
                    }
                }

                if (esSeleccionado) {
                    // EFECTO PADRE: Punto seleccionado (Círculo sólido grande con anillo brillante/halo)
                    g2.setColor(new Color(colorJugadorActual.getRed(), colorJugadorActual.getGreen(), colorJugadorActual.getBlue(), 80));
                    g2.fillOval(px - 14, py - 14, 28, 28); // Halo exterior
                    g2.setColor(colorJugadorActual);
                    g2.fillOval(px - 9, py - 9, 18, 18);    // Núcleo sólido
                } else if (esDisponible) {
                    // EFECTO PADRE: Jugada posible, visible para todos los jugadores (atenuada para quien no está en turno)
                    int opacidad = esTurnoLocal ? 255 : 120;
                    g2.setColor(new Color(colorJugadorActual.getRed(), colorJugadorActual.getGreen(), colorJugadorActual.getBlue(), 50));
                    g2.drawOval(px - 12, py - 12, 24, 24); // Anillo externo
                    g2.setColor(new Color(colorJugadorActual.getRed(), colorJugadorActual.getGreen(), colorJugadorActual.getBlue(), opacidad));
                    g2.fillOval(px - 7, py - 7, 14, 14);    // Centro
                } else {
                    // Punto normal inactivo
                    g2.setColor(new Color(148, 163, 184));
                    g2.fillOval(px - 5, py - 5, 10, 10);
                }
            }
        }
    }

}