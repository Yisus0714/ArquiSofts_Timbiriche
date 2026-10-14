/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author luisb
 */
import dto.JugadorPanelDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TarjetaJugadorPanel extends JPanel {

    private final JugadorPanelDTO jugador;

    public TarjetaJugadorPanel(JugadorPanelDTO jugador) {
        this.jugador = jugador;

        setOpaque(false);
        setPreferredSize(new Dimension(170, 220));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        construirUI();
    }

    private void construirUI() {
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        Color colorJugador = jugador.getColor() != null ? jugador.getColor() : new Color(37, 99, 235);

        // 1. Contenedor del Avatar / Símbolo
        JLabel lblAvatar = new JLabel();
        lblAvatar.setAlignmentX(CENTER_ALIGNMENT);
        lblAvatar.setOpaque(true);
        lblAvatar.setPreferredSize(new Dimension(52, 52));
        lblAvatar.setMaximumSize(new Dimension(52, 52));
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);

        // Color de fondo suave derivado del color del jugador
        Color fondoAvatar = new Color(colorJugador.getRed(), colorJugador.getGreen(), colorJugador.getBlue(), 35);
        lblAvatar.setBackground(fondoAvatar);

        if (jugador.getAvatar() != null) {
            lblAvatar.setIcon(RecursosAvatar.icono(jugador.getAvatar(), 36));
        } else {
            lblAvatar.setText("★");
            lblAvatar.setFont(new Font("SansSerif", Font.BOLD, 18));
            lblAvatar.setForeground(colorJugador);
        }

        // 2. Nombre del Jugador y Badge "TÚ"
        JPanel pnlNombre = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        pnlNombre.setOpaque(false);

        JLabel lblNombre = new JLabel(jugador.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblNombre.setForeground(new Color(30, 41, 59));
        pnlNombre.add(lblNombre);

        // Si esTu es true, agrega la etiqueta visual "TÚ"
        if (jugador.isEsTu()) {
            JLabel lblTu = new JLabel(" TÚ ");
            lblTu.setFont(new Font("SansSerif", Font.BOLD, 9));
            lblTu.setForeground(Color.WHITE);
            lblTu.setOpaque(true);
            lblTu.setBackground(new Color(37, 99, 235));
            pnlNombre.add(lblTu);
        }

        // 3. Indicador de Color Asignado
        JPanel pnlColor = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        pnlColor.setOpaque(false);

        JLabel lblPuntoColor = new JLabel("●");
        lblPuntoColor.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblPuntoColor.setForeground(colorJugador);

        JLabel lblTextoColor = new JLabel(obtenerNombreHexOColor(colorJugador));
        lblTextoColor.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblTextoColor.setForeground(new Color(100, 116, 139));

        pnlColor.add(lblPuntoColor);
        pnlColor.add(lblTextoColor);

        // 4. Badge de Estado basado únicamente en votoFavor
        JLabel lblEstado = new JLabel();
        lblEstado.setAlignmentX(CENTER_ALIGNMENT);
        lblEstado.setOpaque(true);
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEstado.setPreferredSize(new Dimension(140, 28));
        lblEstado.setMaximumSize(new Dimension(140, 28));

//        if (jugador.) {
//            lblEstado.setText("✓  Listo ✓");
//            lblEstado.setForeground(new Color(30, 41, 59));
//            lblEstado.setBackground(fondoAvatar);
//        } else {
//            lblEstado.setText("◷  Esperando...");
//            lblEstado.setForeground(new Color(100, 116, 139));
//            lblEstado.setBackground(new Color(241, 245, 249));
//        }

        // Organización vertical de la tarjeta
        add(lblAvatar);
        add(Box.createVerticalStrut(12));
        add(pnlNombre);
        add(Box.createVerticalStrut(4));
        add(pnlColor);
        add(Box.createVerticalGlue());
        add(lblEstado);
    }

    private String obtenerNombreHexOColor(Color color) {
        if (color.equals(new Color(37, 99, 235))) return "Azul Royal";
        if (color.equals(new Color(225, 29, 72))) return "Rojo Carmesí";
        if (color.equals(new Color(217, 119, 6))) return "Ámbar Solar";
        if (color.equals(new Color(99, 102, 241))) return "Índigo Puro";
        return "Color Asignado";
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo Blanco Redondeado
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 16, 16));

        // Borde fino
        g2.setColor(new Color(226, 232, 240));
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 16, 16));

        g2.dispose();
        super.paintComponent(g);
    }
}