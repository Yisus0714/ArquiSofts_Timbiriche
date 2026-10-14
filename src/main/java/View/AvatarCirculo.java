/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Dominio.Avatar;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Imagen del avatar dentro de un círculo con el color del jugador.
 * Opcionalmente muestra una estrella en la esquina, para marcar a los ganadores.
 *
 * @author luisb
 */
public class AvatarCirculo extends JPanel {

    private final Color color;
    private final ImageIcon icono;
    private final int tamanoIcono;
    private final boolean ganador;

    public AvatarCirculo(Avatar avatar, Color color, int tamano, boolean ganador) {
        this.color = color != null ? color : new Color(37, 99, 235);
        this.tamanoIcono = (int) (tamano * 0.65);
        this.icono = RecursosAvatar.icono(avatar, tamanoIcono);
        this.ganador = ganador;

        Dimension dimension = new Dimension(tamano, tamano);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setOpaque(false);
    }

    public AvatarCirculo(Avatar avatar, Color color, int tamano) {
        this(avatar, color, tamano, false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Fondo suave y aro con el color del jugador
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 45));
        g2.fillOval(0, 0, w - 1, h - 1);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(3f));
        g2.drawOval(2, 2, w - 5, h - 5);

        icono.paintIcon(this, g2, (w - tamanoIcono) / 2, (h - tamanoIcono) / 2);

        if (ganador) {
            int d = Math.max(18, w / 4);
            int x = w - d;
            g2.setColor(new Color(254, 215, 170));
            g2.fillOval(x, 0, d, d);
            g2.setColor(new Color(194, 65, 12));
            g2.setFont(new Font("SansSerif", Font.BOLD, d - 6));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString("★", x + (d - fm.stringWidth("★")) / 2, (d + fm.getAscent() - fm.getDescent()) / 2);
        }

        g2.dispose();
    }

}
