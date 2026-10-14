/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Control.ControlFinal;
import Model.IConsultaFinal;
import dto.JugadorFinalDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pantalla de resultados finales. Todos los jugadores empatados en el primer
 * puesto se muestran con tarjeta grande; el resto aparece en tarjetas pequeñas.
 *
 * @author luisb
 */
public class VistaFinal extends JFrame {

    private static final Color FONDO = new Color(248, 250, 252);
    private static final Color TEXTO = new Color(15, 23, 42);
    private static final Color TEXTO_SUAVE = new Color(100, 116, 139);
    private static final Color AZUL = new Color(0, 74, 198);

    private final ControlFinal control;
    private final IConsultaFinal consulta;

    public VistaFinal(ControlFinal control, IConsultaFinal consulta) {
        this.control = control;
        this.consulta = consulta;

        setTitle("Timbiriche - Resultados Finales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO);

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(FONDO);

        mainPanel.add(crearEncabezado(), BorderLayout.NORTH);

        JPanel pnlCentro = new JPanel(new GridBagLayout());
        pnlCentro.setOpaque(false);

        try {
            pnlCentro.add(crearContenido(consulta.getResultados()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al cargar los resultados", JOptionPane.ERROR_MESSAGE);
        }

        mainPanel.add(pnlCentro, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel crearEncabezado() {
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 12));
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));

        JPanel badgeLogo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(37, 99, 235));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            }
        };
        badgeLogo.setPreferredSize(new Dimension(32, 32));
        badgeLogo.setOpaque(false);
        JLabel lblHash = new JLabel("#", SwingConstants.CENTER);
        lblHash.setForeground(Color.WHITE);
        lblHash.setFont(new Font("SansSerif", Font.BOLD, 16));
        badgeLogo.setLayout(new BorderLayout());
        badgeLogo.add(lblHash, BorderLayout.CENTER);

        pnlHeader.add(badgeLogo);
        return pnlHeader;
    }

    /**
     * Título, ganadores, demás jugadores y botón de salida.
     */
    private JPanel crearContenido(List<JugadorFinalDTO> resultados) {
        List<JugadorFinalDTO> ganadores = new ArrayList<>();
        List<JugadorFinalDTO> demas = new ArrayList<>();
        for (JugadorFinalDTO j : resultados) {
            if (j.isGanador()) {
                ganadores.add(j);
            } else {
                demas.add(j);
            }
        }

        JPanel pnlContenido = new JPanel();
        pnlContenido.setLayout(new BoxLayout(pnlContenido, BoxLayout.Y_AXIS));
        pnlContenido.setOpaque(false);

        boolean empate = ganadores.size() > 1;

        JLabel lblTitulo = new JLabel(empate ? "¡Empate!" : "¡Tenemos un ganador!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitulo.setForeground(TEXTO);
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel(empate ? "Los jugadores empatados comparten el primer lugar" : "Resultados finales de la partida", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setForeground(TEXTO_SUAVE);
        lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

        JPanel pnlGanadores = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnlGanadores.setOpaque(false);
        pnlGanadores.setAlignmentX(CENTER_ALIGNMENT);
        for (JugadorFinalDTO j : ganadores) {
            pnlGanadores.add(crearTarjetaGanador(j));
        }

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBackground(AZUL);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setAlignmentX(CENTER_ALIGNMENT);
        btnSalir.addActionListener(e -> control.salir());

        pnlContenido.add(lblTitulo);
        pnlContenido.add(Box.createVerticalStrut(6));
        pnlContenido.add(lblSubtitulo);
        pnlContenido.add(Box.createVerticalStrut(30));
        pnlContenido.add(pnlGanadores);

        if (!demas.isEmpty()) {
            JPanel pnlDemas = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            pnlDemas.setOpaque(false);
            pnlDemas.setAlignmentX(CENTER_ALIGNMENT);
            for (JugadorFinalDTO j : demas) {
                pnlDemas.add(crearTarjetaJugador(j));
            }
            pnlContenido.add(Box.createVerticalStrut(25));
            pnlContenido.add(pnlDemas);
        }

        pnlContenido.add(Box.createVerticalStrut(35));
        pnlContenido.add(btnSalir);
        return pnlContenido;
    }

    /**
     * Tarjeta grande de un jugador ganador.
     */
    private JPanel crearTarjetaGanador(JugadorFinalDTO dto) {
        Color color = dto.getColor() != null ? dto.getColor() : new Color(37, 99, 235);

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setPreferredSize(new Dimension(230, 290));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2, true),
            BorderFactory.createEmptyBorder(22, 15, 18, 15)
        ));

        AvatarCirculo avatar = new AvatarCirculo(dto.getAvatar(), color, 110, true);
        avatar.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblPuesto = new JLabel(dto.getPuesto() + "° LUGAR", SwingConstants.CENTER);
        lblPuesto.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblPuesto.setForeground(color);
        lblPuesto.setAlignmentX(CENTER_ALIGNMENT);

        JPanel pnlNombre = crearLineaNombre(dto, color, 20, FlowLayout.CENTER);

        JLabel lblPuntos = new JLabel(String.valueOf(dto.getPuntaje()), SwingConstants.CENTER);
        lblPuntos.setFont(new Font("SansSerif", Font.BOLD, 44));
        lblPuntos.setForeground(color);
        lblPuntos.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblEtiquetaPuntos = new JLabel("PUNTOS", SwingConstants.CENTER);
        lblEtiquetaPuntos.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblEtiquetaPuntos.setForeground(new Color(148, 163, 184));
        lblEtiquetaPuntos.setAlignmentX(CENTER_ALIGNMENT);

        tarjeta.add(avatar);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(lblPuesto);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(pnlNombre);
        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(lblPuntos);
        tarjeta.add(lblEtiquetaPuntos);
        return tarjeta;
    }

    /**
     * Tarjeta pequeña de un jugador que no ganó.
     */
    private JPanel crearTarjetaJugador(JugadorFinalDTO dto) {
        Color color = dto.getColor() != null ? dto.getColor() : new Color(37, 99, 235);

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.X_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setPreferredSize(new Dimension(280, 75));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(new BoxLayout(pnlInfo, BoxLayout.Y_AXIS));
        pnlInfo.setOpaque(false);
        pnlInfo.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        JPanel pnlNombre = crearLineaNombre(dto, color, 14, FlowLayout.LEFT);
        pnlNombre.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblPuesto = new JLabel(dto.getPuesto() + "° LUGAR");
        lblPuesto.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblPuesto.setForeground(TEXTO_SUAVE);
        lblPuesto.setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 0));
        lblPuesto.setAlignmentX(LEFT_ALIGNMENT);

        pnlInfo.add(pnlNombre);
        pnlInfo.add(lblPuesto);

        JPanel pnlPuntos = new JPanel();
        pnlPuntos.setLayout(new BoxLayout(pnlPuntos, BoxLayout.Y_AXIS));
        pnlPuntos.setOpaque(false);

        JLabel lblTituloPuntos = new JLabel("PUNTOS");
        lblTituloPuntos.setFont(new Font("SansSerif", Font.BOLD, 9));
        lblTituloPuntos.setForeground(new Color(148, 163, 184));
        lblTituloPuntos.setAlignmentX(RIGHT_ALIGNMENT);

        JLabel lblValorPuntos = new JLabel(String.format("%02d", dto.getPuntaje()));
        lblValorPuntos.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblValorPuntos.setForeground(color);
        lblValorPuntos.setAlignmentX(RIGHT_ALIGNMENT);

        pnlPuntos.add(lblTituloPuntos);
        pnlPuntos.add(lblValorPuntos);

        tarjeta.add(new AvatarCirculo(dto.getAvatar(), color, 50));
        tarjeta.add(pnlInfo);
        tarjeta.add(Box.createHorizontalGlue());
        tarjeta.add(pnlPuntos);
        return tarjeta;
    }

    /**
     * Nombre del jugador y, si es el jugador local, la etiqueta TÚ con su color.
     */
    private JPanel crearLineaNombre(JugadorFinalDTO dto, Color color, int tamanoFuente, int alineacion) {
        JPanel pnlLinea = new JPanel(new FlowLayout(alineacion, 5, 0));
        pnlLinea.setOpaque(false);

        JLabel lblNombre = new JLabel(dto.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, tamanoFuente));
        lblNombre.setForeground(TEXTO);
        pnlLinea.add(lblNombre);

        if (dto.isEsTu()) {
            JLabel lblTu = new JLabel("TÚ");
            lblTu.setFont(new Font("SansSerif", Font.BOLD, 9));
            lblTu.setForeground(textoLegibleSobre(color));
            lblTu.setOpaque(true);
            lblTu.setBackground(color);
            lblTu.setBorder(BorderFactory.createEmptyBorder(1, 4, 1, 4));
            pnlLinea.add(lblTu);
        }
        return pnlLinea;
    }

    private static Color textoLegibleSobre(Color fondo) {
        double luminancia = (0.299 * fondo.getRed()
                           + 0.587 * fondo.getGreen()
                           + 0.114 * fondo.getBlue()) / 255.0;
        return luminancia > 0.6 ? new Color(15, 23, 42) : Color.WHITE;
    }

}
