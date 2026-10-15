/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.IConsultaJugadores;
import dto.JugadorPanelDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PanelJugadores extends JPanel {

    private final IConsultaJugadores consultaJugadores;
    private JPanel pnlContenedorCartas;

    public PanelJugadores(IConsultaJugadores consultaJugadores) {
        this.consultaJugadores = consultaJugadores;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 20, 15, 20));

        initUI();
        actualizarDatos();
    }

    private void initUI() {
        pnlContenedorCartas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlContenedorCartas.setBackground(Color.WHITE);
        add(pnlContenedorCartas, BorderLayout.CENTER);
    }

    public void actualizarDatos() {
        try {
            List<JugadorPanelDTO> jugadores = consultaJugadores.getJugadores();
            pnlContenedorCartas.removeAll();

            if (jugadores != null) {
                for (JugadorPanelDTO j : jugadores) {
                    pnlContenedorCartas.add(crearTarjetaJugador(j));
                }
            }

            pnlContenedorCartas.revalidate();
            pnlContenedorCartas.repaint();
        } catch (Exception e) {
            // Manejo de excepción silencioso o log
        }
    }

    private JPanel crearTarjetaJugador(JugadorPanelDTO dto) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.X_AXIS));
        tarjeta.setPreferredSize(new Dimension(280, 75));
        
        Color borderColor = dto.isEnTurno()? (dto.getColor() != null ? dto.getColor() : new Color(37, 99, 235)) : new Color(226, 232, 240);
        int borderWidth = dto.isEnTurno() ? 2 : 1;

        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor, borderWidth, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        tarjeta.setBackground(dto.isEnTurno() ? new Color(248, 250, 252) : Color.WHITE);

        // Avatar del jugador dentro de un círculo con su color
        AvatarCirculo pnlAvatar = new AvatarCirculo(dto.getAvatar(), dto.getColor(), 50);

        // Información de texto
        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(new BoxLayout(pnlInfo, BoxLayout.Y_AXIS));
        pnlInfo.setOpaque(false);
        pnlInfo.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        Color colorJugador = dto.getColor() != null ? dto.getColor() : new Color(37, 99, 235);

        JPanel pnlLineaNombre = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlLineaNombre.setOpaque(false);
        pnlLineaNombre.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblNombre = new JLabel(dto.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombre.setForeground(new Color(15, 23, 42));
        pnlLineaNombre.add(lblNombre);

        if (dto.isEsTu()) {
            JLabel lblTu = new JLabel("TÚ");
            lblTu.setFont(new Font("SansSerif", Font.BOLD, 9));
            lblTu.setForeground(textoLegibleSobre(colorJugador));
            lblTu.setOpaque(true);
            lblTu.setBackground(colorJugador);
            lblTu.setBorder(BorderFactory.createEmptyBorder(1, 4, 1, 4));
            pnlLineaNombre.add(lblTu);
        }
        pnlInfo.add(pnlLineaNombre);

        if (dto.isEnTurno()) {
            JLabel lblTurno = new JLabel("● EN TURNO");
            lblTurno.setFont(new Font("SansSerif", Font.BOLD, 10));
            lblTurno.setForeground(colorJugador);
            lblTurno.setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 0));
            lblTurno.setAlignmentX(LEFT_ALIGNMENT);
            pnlInfo.add(lblTurno);
        }

        // Puntos acumulados
        JPanel pnlPuntos = new JPanel();
        pnlPuntos.setLayout(new BoxLayout(pnlPuntos, BoxLayout.Y_AXIS));
        pnlPuntos.setOpaque(false);
        
        JLabel lblTituloPuntos = new JLabel("PUNTOS");
        lblTituloPuntos.setFont(new Font("SansSerif", Font.BOLD, 9));
        lblTituloPuntos.setForeground(new Color(148, 163, 184));
        lblTituloPuntos.setAlignmentX(RIGHT_ALIGNMENT);

        JLabel lblValorPuntos = new JLabel(String.format("%02d", dto.getPuntaje()));
        lblValorPuntos.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblValorPuntos.setForeground(dto.getColor() != null ? dto.getColor() : new Color(37, 99, 235));
        lblValorPuntos.setAlignmentX(RIGHT_ALIGNMENT);

        pnlPuntos.add(lblTituloPuntos);
        pnlPuntos.add(lblValorPuntos);

        tarjeta.add(pnlAvatar);
        tarjeta.add(pnlInfo);
        tarjeta.add(Box.createHorizontalGlue());
        tarjeta.add(pnlPuntos);

        return tarjeta;
    }
    
    private static Color textoLegibleSobre(Color fondo) {
        double luminancia = (0.299 * fondo.getRed()
                           + 0.587 * fondo.getGreen()
                           + 0.114 * fondo.getBlue()) / 255.0;
        return luminancia > 0.6 ? new Color(15, 23, 42) : Color.WHITE;
    }

}