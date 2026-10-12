package vista.votacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * Pantalla de votación para terminar la partida.
 *
 * Una sola pantalla con tres estados:
 * - V01 mostrarVotacion():  "¿Terminar la partida?"
 * - V02 mostrarTerminada(): "Partida terminada por acuerdo"
 * - V03 mostrarContinua():  "La partida continúa"
 */
public class PanelVotacion extends JPanel {

    // Estilos de botón
    public static final int PRIMARIO = 0;
    public static final int SECUNDARIO = 1;
    public static final int PELIGRO = 2;

    private static final Color AZUL = new Color(47, 101, 232);
    private static final Color AZUL_CLARO = new Color(232, 240, 254);
    private static final Color ROJO = new Color(229, 45, 50);
    private static final Color FONDO = new Color(245, 247, 252);
    private static final Color TEXTO = new Color(18, 24, 38);
    private static final Color TEXTO_SUAVE = new Color(100, 110, 125);

    private final JLabel lblIcono = new JLabel();
    private final JLabel lblTitulo = new JLabel();
    private final JLabel lblSubtitulo = new JLabel();
    private final JLabel lblSi = crearConteo();
    private final JLabel lblNo = crearConteo();
    private final JLabel lblPie = new JLabel(" ");

    private final JButton btnSi = crearBoton("Sí, terminar", PRIMARIO);
    private final JButton btnNo = crearBoton("No, continuar", SECUNDARIO);
    private final JButton btnVerResultados = crearBoton("Ver resultados finales  →", PRIMARIO);
    private final JButton btnRegresar = crearBoton("Regresar al tablero", PRIMARIO);

    private String prefijoPie = "";

    public PanelVotacion() {
        setBackground(FONDO);
        setLayout(new GridBagLayout());

        JPanel columna = new JPanel();
        columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
        columna.setOpaque(false);

        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(TEXTO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSubtitulo.setForeground(TEXTO_SUAVE);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pnlConteo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pnlConteo.setOpaque(false);
        pnlConteo.add(lblSi);
        pnlConteo.add(lblNo);
        pnlConteo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        pnlBotones.setOpaque(false);
        pnlBotones.add(btnSi);
        pnlBotones.add(btnNo);
        pnlBotones.add(btnVerResultados);
        pnlBotones.add(btnRegresar);
        pnlBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblPie.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblPie.setForeground(new Color(150, 158, 172));
        lblPie.setAlignmentX(Component.CENTER_ALIGNMENT);

        columna.add(lblIcono);
        columna.add(Box.createVerticalStrut(14));
        columna.add(lblTitulo);
        columna.add(Box.createVerticalStrut(8));
        columna.add(lblSubtitulo);
        columna.add(Box.createVerticalStrut(18));
        columna.add(pnlConteo);
        columna.add(Box.createVerticalStrut(22));
        columna.add(pnlBotones);
        columna.add(Box.createVerticalStrut(16));
        columna.add(lblPie);
        add(columna);

        // TODO (Controlador): btnSi.addActionListener(...)  -> emitir voto a favor de terminar
        // TODO (Controlador): btnNo.addActionListener(...)  -> emitir voto a favor de continuar
        // TODO (Controlador): btnVerResultados.addActionListener(...) -> mostrar PanelPosiciones
        // TODO (Controlador): btnRegresar.addActionListener(...) -> volver al tablero

        mostrarVotacion(0, 0);
    }

    // ESTADOS DE LA PANTALLA

    /** V01 - ¿Terminar la partida? (el jugador puede votar). */
    public void mostrarVotacion(int votosSi, int votosNo) {
        mostrar("documento", "¿Terminar la partida?",
                "Todos los jugadores deben estar de acuerdo para finalizar y calcular la puntuación final.",
                votosSi, votosNo, true, false, false);
        prefijoPie = "";
        lblPie.setText("Votación anónima en tiempo real");
        setVotoEmitido(false);
    }

    /** V02 - Partida terminada por acuerdo. */
    public void mostrarTerminada(int votosSi, int votosNo) {
        mostrar("check", "Partida terminada por acuerdo",
                "Todos los jugadores han votado a favor de terminar la partida anticipadamente. "
                        + "Calculando resultados oficiales...",
                votosSi, votosNo, false, true, false);
        prefijoPie = "Redirigiendo a resultados en ";
        setSegundosRestantes(3);
    }

    /** V03 - La partida continúa. */
    public void mostrarContinua(int votosSi, int votosNo) {
        mostrar("alerta", "La partida continúa",
                "Al menos un jugador votó por continuar el juego. La votación se ha desestimado.",
                votosSi, votosNo, false, false, true);
        prefijoPie = "Reanudando partida en ";
        setSegundosRestantes(3);
    }

    public void setSegundosRestantes(int segundos) {
        lblPie.setText(prefijoPie + segundos + "s...");
    }

    /** Actualiza solo los contadores Sí/No sin cambiar de estado. */
    public void actualizarVotos(int votosSi, int votosNo) {
        setConteo(lblSi, "Sí", votosSi);
        setConteo(lblNo, "No", votosNo);
    }

    public void setVotoEmitido(boolean emitido) {
        btnSi.setEnabled(!emitido);
        btnNo.setEnabled(!emitido);
    }



    public JButton getBtnSi() {
        return btnSi;
    }

    public JButton getBtnNo() {
        return btnNo;
    }

    public JButton getBtnVerResultados() {
        return btnVerResultados;
    }

    public JButton getBtnRegresar() {
        return btnRegresar;
    }



    public static JButton crearBoton(String texto, int estilo) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color fondo = switch (estilo) {
                    case PELIGRO -> ROJO;
                    case SECUNDARIO -> Color.WHITE;
                    default -> AZUL;
                };
                if (!isEnabled()) {
                    fondo = estilo == SECUNDARIO ? new Color(245, 246, 248) : new Color(170, 185, 215);
                } else if (getModel().isPressed()) {
                    fondo = fondo.darker();
                } else if (getModel().isRollover() && estilo != SECUNDARIO) {
                    fondo = fondo.brighter();
                }

                g2.setColor(fondo);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 10, 10));
                if (estilo == SECUNDARIO) {
                    g2.setColor(new Color(220, 224, 232));
                    g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 10, 10));
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setForeground(estilo == SECUNDARIO ? TEXTO : Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(new EmptyBorder(11, 22, 11, 22));
        return boton;
    }


    public static ImageIcon crearIcono(String tipo, Color trazo, Color fondo) {
        int d = 36;
        BufferedImage img = new BufferedImage(d, d, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2.setColor(fondo);
        g2.fillOval(0, 0, d, d);

        g2.setColor(trazo);
        g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        float c = d / 2f;

        switch (tipo) {
            case "check" -> {
                Path2D p = new Path2D.Float();
                p.moveTo(c - 6, c + 0.5f);
                p.lineTo(c - 1.5f, c + 5);
                p.lineTo(c + 6.5f, c - 4.5f);
                g2.draw(p);
            }
            case "alerta" -> {
                Path2D p = new Path2D.Float();
                p.moveTo(c, c - 7);
                p.lineTo(c + 7.5f, c + 6);
                p.lineTo(c - 7.5f, c + 6);
                p.closePath();
                g2.draw(p);
                g2.drawLine(Math.round(c), Math.round(c - 2), Math.round(c), Math.round(c + 1.5f));
                g2.fillOval(Math.round(c - 1), Math.round(c + 3), 2, 2);
            }
            default -> { // documento
                g2.drawRoundRect(Math.round(c - 6), Math.round(c - 7.5f), 12, 15, 3, 3);
                g2.drawLine(Math.round(c - 3), Math.round(c - 2), Math.round(c + 3), Math.round(c - 2));
                g2.drawLine(Math.round(c - 3), Math.round(c + 2), Math.round(c + 3), Math.round(c + 2));
            }
        }
        g2.dispose();
        return new ImageIcon(img);
    }



    private void mostrar(String icono, String titulo, String subtitulo,
                         int votosSi, int votosNo,
                         boolean verVotar, boolean verResultados, boolean verRegresar) {
        lblIcono.setIcon(crearIcono(icono, AZUL, AZUL_CLARO));
        lblTitulo.setText(titulo);
        lblSubtitulo.setText("<html><div style='text-align:center; width:300px'>"
                + subtitulo + "</div></html>");
        actualizarVotos(votosSi, votosNo);

        btnSi.setVisible(verVotar);
        btnNo.setVisible(verVotar);
        btnVerResultados.setVisible(verResultados);
        btnRegresar.setVisible(verRegresar);

        revalidate();
        repaint();
    }

    private static JLabel crearConteo() {
        JLabel pildora = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(240, 243, 248));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        pildora.setOpaque(false);
        pildora.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pildora.setForeground(TEXTO_SUAVE);
        pildora.setBorder(new EmptyBorder(5, 14, 5, 14));
        return pildora;
    }
//s
    private static void setConteo(JLabel pildora, String etiqueta, int votos) {
        pildora.setText("<html><font color='#2F65E8'>●</font>&nbsp; "
                + etiqueta + ": <b>" + votos + "</b></html>");
    }
}