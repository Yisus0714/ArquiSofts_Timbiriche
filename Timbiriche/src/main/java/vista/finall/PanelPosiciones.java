/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.finall;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public class PanelPosiciones extends JPanel {

    private final List<ResultadosJugador> listaJugadores;

    /**
     * @param jugadores Lista con de 2 a 4 jugadores.
     */
    public PanelPosiciones(List<ResultadosJugador> jugadores) {
        if (jugadores == null || jugadores.size() < 2 || jugadores.size() > 4) {
            throw new IllegalArgumentException("El panel admite unicamente entre 2 y 4 jugadores.");
        }

        // Crear copia y ordenar descendentemente por puntos
        this.listaJugadores = new ArrayList<>(jugadores);
        Collections.sort(this.listaJugadores);

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 253));

        // Panel con scroll para garantizar buena presentacion en pantallas pequenas
        JPanel pnlContenido = new JPanel();
        pnlContenido.setLayout(new BoxLayout(pnlContenido, BoxLayout.Y_AXIS));
        pnlContenido.setOpaque(false);
        pnlContenido.setBorder(new EmptyBorder(30, 40, 30, 40));

        // ---------------------------------------------------------------------
        // ENCABEZADO "¡Partida Finalizada!"
        // ---------------------------------------------------------------------
        JLabel lblTitulo = new JLabel("¡Partida Finalizada!");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(18, 24, 38));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Resultados oficiales de la partida");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(100, 110, 125));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlContenido.add(lblTitulo);
        pnlContenido.add(Box.createVerticalStrut(4));
        pnlContenido.add(lblSubtitulo);
        pnlContenido.add(Box.createVerticalStrut(24));

        // ---------------------------------------------------------------------
        // SECCION GANADORES Y STANDING
        // ---------------------------------------------------------------------
        int maxPuntos = this.listaJugadores.get(0).getPuntos();

        // Identificar si hay empate en el primer puesto
        List<ResultadosJugador> ganadores = new ArrayList<>();
        List<ResultadosJugador> otrosJugadores = new ArrayList<>();

        for (ResultadosJugador j : this.listaJugadores) {
            if (j.getPuntos() == maxPuntos) {
                ganadores.add(j);
            } else {
                otrosJugadores.add(j);
            }
        }

        // Agregar tarjetas grandes de ganadores (mismo tamano si hay empate)
        for (ResultadosJugador ganador : ganadores) {
            pnlContenido.add(crearTarjetaGanador(ganador));
            pnlContenido.add(Box.createVerticalStrut(14));
        }

        // Agregar seccion "STANDING GENERAL" si existen otros jugadores fuera del 1° puesto
        if (!otrosJugadores.isEmpty()) {
            pnlContenido.add(Box.createVerticalStrut(10));

            JLabel lblStanding = new JLabel("STANDING GENERAL");
            lblStanding.setFont(new Font("SansSerif", Font.BOLD, 11));
            lblStanding.setForeground(new Color(110, 118, 132));
            lblStanding.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Alinear titulo de standing a la izquierda dentro del ancho maximo
            JPanel pnlSubTituloStanding = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            pnlSubTituloStanding.setOpaque(false);
            pnlSubTituloStanding.setMaximumSize(new Dimension(520, 20));
            pnlSubTituloStanding.add(lblStanding);

            pnlContenido.add(pnlSubTituloStanding);
            pnlContenido.add(Box.createVerticalStrut(12));

            int posicion = ganadores.size() + 1;
            for (ResultadosJugador j : otrosJugadores) {
                pnlContenido.add(crearTarjetaEstandar(j, posicion));
                pnlContenido.add(Box.createVerticalStrut(10));
                posicion++;
            }
        }

        // ---------------------------------------------------------------------
        // BOTON SALIR
        // ---------------------------------------------------------------------
        pnlContenido.add(Box.createVerticalStrut(16));
        JButton btnSalir = new JButton(" Salir");
        btnSalir.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSalir.setForeground(new Color(30, 35, 45));
        btnSalir.setBackground(new Color(230, 236, 250));
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        btnSalir.setMaximumSize(new Dimension(520, 42));
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlContenido.add(btnSalir);

        JScrollPane scrollPane = new JScrollPane(pnlContenido);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);
    }

    // =========================================================================
    // CONSTRUCCION DE TARJETAS
    // =========================================================================

    /**
     * Construye la tarjeta destacada para el 1° PUESTO (Soporta empates).
     */
    private JPanel crearTarjetaGanador(ResultadosJugador jugador) {
        JPanel tarjeta = new JPanel(new BorderLayout(16, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo blanco redondeado
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));

                // Banda lateral del color del jugador
                g2.setColor(jugador.getColor());
                g2.fill(new RoundRectangle2D.Float(0, 0, 8, getHeight(), 16, 16));
                g2.fillRect(4, 0, 4, getHeight());

                g2.dispose();
            }
        };
        tarjeta.setOpaque(false);
        tarjeta.setPreferredSize(new Dimension(520, 95));
        tarjeta.setMaximumSize(new Dimension(520, 95));
        tarjeta.setBorder(new EmptyBorder(12, 20, 12, 20));

        // 1. Avatar Izquierda
        JLabel lblAvatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(jugador.getColor());
                g2.fill(new Ellipse2D.Float(0, 0, 54, 54));

                // Icono de medalla
                g2.setColor(new Color(245, 210, 120));
                g2.fill(new Ellipse2D.Float(38, 0, 16, 16));
                g2.setColor(new Color(120, 80, 20));
                g2.setFont(new Font("SansSerif", Font.BOLD, 9));
                g2.drawString("★", 43, 12);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        lblAvatar.setPreferredSize(new Dimension(56, 56));
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvatar.setForeground(Color.WHITE);
        lblAvatar.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblAvatar.setText(jugador.getNombre().isEmpty() ? "?" : String.valueOf(jugador.getNombre().charAt(0)).toUpperCase());

        // 2. Centro: Badges + Nombre
        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.Y_AXIS));
        pnlCentro.setOpaque(false);

        JPanel pnlBadges = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        pnlBadges.setOpaque(false);
        pnlBadges.setAlignmentX(Component.LEFT_ALIGNMENT);

        pnlBadges.add(crearBadge("1° PUESTO", new Color(230, 238, 255), jugador.getColor()));
        pnlBadges.add(crearBadge("GANADOR", jugador.getColor(), Color.WHITE));
        if (jugador.isEsUsuarioActual()) {
            pnlBadges.add(crearBadge("TU", new Color(15, 80, 230), Color.WHITE));
        }

        JLabel lblNombre = new JLabel(jugador.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblNombre.setForeground(new Color(20, 25, 35));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        pnlCentro.add(pnlBadges);
        pnlCentro.add(Box.createVerticalStrut(4));
        pnlCentro.add(lblNombre);

        // 3. Derecha: Puntos
        JPanel pnlPuntos = new JPanel();
        pnlPuntos.setLayout(new BoxLayout(pnlPuntos, BoxLayout.Y_AXIS));
        pnlPuntos.setOpaque(false);

        JLabel lblTituloPuntos = new JLabel("PUNTOS");
        lblTituloPuntos.setFont(new Font("SansSerif", Font.BOLD, 9));
        lblTituloPuntos.setForeground(new Color(130, 138, 150));
        lblTituloPuntos.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JPanel pnlValorPuntos = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 0));
        pnlValorPuntos.setOpaque(false);

        JLabel lblNumPuntos = new JLabel(String.valueOf(jugador.getPuntos()));
        lblNumPuntos.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblNumPuntos.setForeground(jugador.getColor());

        JLabel lblPts = new JLabel("pts");
        lblPts.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblPts.setForeground(new Color(130, 138, 150));

        pnlValorPuntos.add(lblNumPuntos);
        pnlValorPuntos.add(lblPts);

        pnlPuntos.add(lblTituloPuntos);
        pnlPuntos.add(pnlValorPuntos);

        tarjeta.add(lblAvatar, BorderLayout.WEST);
        tarjeta.add(pnlCentro, BorderLayout.CENTER);
        tarjeta.add(pnlPuntos, BorderLayout.EAST);

        return tarjeta;
    }

    /**
     * Construye las tarjetas compactas del Standing General (Puestos 2°, 3°, 4°).
     */
    private JPanel crearTarjetaEstandar(ResultadosJugador jugador, int posicion) {
        JPanel tarjeta = new JPanel(new BorderLayout(12, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.dispose();
            }
        };
        tarjeta.setOpaque(false);
        tarjeta.setPreferredSize(new Dimension(520, 52));
        tarjeta.setMaximumSize(new Dimension(520, 52));
        tarjeta.setBorder(new EmptyBorder(6, 16, 6, 16));

        // 1. Izquierda: Numero de posicion + Avatar circular
        JPanel pnlIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        pnlIzquierda.setOpaque(false);

        JLabel lblPosicion = new JLabel(String.valueOf(posicion)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(240, 243, 248));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lblPosicion.setPreferredSize(new Dimension(28, 28));
        lblPosicion.setHorizontalAlignment(SwingConstants.CENTER);
        lblPosicion.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblPosicion.setForeground(new Color(80, 90, 105));

        JLabel lblAvatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(jugador.getColor());
                g2.fill(new Ellipse2D.Float(0, 0, 32, 32));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lblAvatar.setPreferredSize(new Dimension(32, 32));
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvatar.setForeground(Color.WHITE);
        lblAvatar.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblAvatar.setText(jugador.getNombre().isEmpty() ? "?" : String.valueOf(jugador.getNombre().charAt(0)).toUpperCase());

        pnlIzquierda.add(lblPosicion);
        pnlIzquierda.add(lblAvatar);

        // 2. Centro: Indicador de punto de color + Nombre
        JPanel pnlCentro = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        pnlCentro.setOpaque(false);

        JLabel lblPuntoColor = new JLabel("●");
        lblPuntoColor.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblPuntoColor.setForeground(jugador.getColor());

        JLabel lblNombre = new JLabel(jugador.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombre.setForeground(new Color(30, 35, 45));

        pnlCentro.add(lblPuntoColor);
        pnlCentro.add(lblNombre);

        // 3. Derecha: Puntos
        JPanel pnlPuntos = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 4));
        pnlPuntos.setOpaque(false);

        JLabel lblNumPuntos = new JLabel(String.valueOf(jugador.getPuntos()));
        lblNumPuntos.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblNumPuntos.setForeground(new Color(20, 25, 35));

        JLabel lblPts = new JLabel("pts");
        lblPts.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblPts.setForeground(new Color(130, 138, 150));

        pnlPuntos.add(lblNumPuntos);
        pnlPuntos.add(lblPts);

        tarjeta.add(pnlIzquierda, BorderLayout.WEST);
        tarjeta.add(pnlCentro, BorderLayout.CENTER);
        tarjeta.add(pnlPuntos, BorderLayout.EAST);

        return tarjeta;
    }

    private JLabel crearBadge(String texto, Color fondo, Color textoColor) {
        JLabel badge = new JLabel(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(fondo);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setOpaque(false);
        badge.setFont(new Font("SansSerif", Font.BOLD, 9));
        badge.setForeground(textoColor);
        badge.setBorder(new EmptyBorder(2, 6, 2, 6));
        return badge;
    }
}