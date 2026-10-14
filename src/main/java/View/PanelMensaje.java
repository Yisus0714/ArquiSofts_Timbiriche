/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;

/**
 * Base de las pantallas de aviso que ocupan toda la ventana de juego:
 * encabezado con el logo y un bloque de contenido centrado.
 *
 * @author luisb
 */
public abstract class PanelMensaje extends JPanel {

    protected static final Color FONDO = new Color(248, 250, 252);
    protected static final Color TEXTO = new Color(15, 23, 42);
    protected static final Color TEXTO_SUAVE = new Color(100, 116, 139);
    protected static final Color AZUL = new Color(0, 74, 198);
    protected static final Color ROJO = new Color(220, 38, 38);

    protected final JPanel pnlContenido;

    protected PanelMensaje() {
        setLayout(new BorderLayout());
        setBackground(FONDO);

        add(crearEncabezado(), BorderLayout.NORTH);

        pnlContenido = new JPanel();
        pnlContenido.setLayout(new BoxLayout(pnlContenido, BoxLayout.Y_AXIS));
        pnlContenido.setOpaque(false);

        JPanel pnlCentro = new JPanel(new GridBagLayout());
        pnlCentro.setOpaque(false);
        pnlCentro.add(pnlContenido);
        add(pnlCentro, BorderLayout.CENTER);
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
     * Círculo de color suave con un símbolo al centro.
     */
    protected JComponent crearIcono(String simbolo, Color color) {
        JPanel icono = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 25));
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
        icono.setOpaque(false);
        Dimension dimension = new Dimension(48, 48);
        icono.setPreferredSize(dimension);
        icono.setMaximumSize(dimension);
        icono.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSimbolo = new JLabel(simbolo, SwingConstants.CENTER);
        lblSimbolo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblSimbolo.setForeground(color);
        icono.add(lblSimbolo, BorderLayout.CENTER);
        return icono;
    }

    protected JLabel crearTitulo(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 26));
        lbl.setForeground(TEXTO);
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        return lbl;
    }

    protected JLabel crearDescripcion(String texto) {
        JLabel lbl = new JLabel("<html><div style='text-align:center;width:340px'>" + texto + "</div></html>", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(TEXTO_SUAVE);
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        return lbl;
    }

    protected JLabel crearNota(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(new Color(148, 163, 184));
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        return lbl;
    }

    protected JButton crearBotonPrimario(String texto, Color fondo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(fondo);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    protected JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(TEXTO);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
            BorderFactory.createEmptyBorder(11, 21, 11, 21)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Fila centrada con los botones que se le pasen.
     */
    protected JPanel crearFilaBotones(JButton... botones) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        fila.setOpaque(false);
        fila.setAlignmentX(CENTER_ALIGNMENT);
        for (JButton btn : botones) {
            fila.add(btn);
        }
        return fila;
    }

}
