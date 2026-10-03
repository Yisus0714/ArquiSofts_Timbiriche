/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.jugadores;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TarjetaJugador extends JPanel {

    private final JLabel lblNombre;
    private final JLabel lblContadorCuadros;
    private final JPanel pnlIndicadorTurno;
    private final JLabel lblTextoTurno;
    private final JLabel lblAvatar;

    private Color colorJugador;
    private boolean esSuTurno;

    /**
     * Tarjeta de Jugador con fondo blanco.
     *
     * @param nombre     Nombre del jugador
     * @param color      Color distintivo del jugador (se usa para el badge de turno)
     * @param iconAvatar Imagen del avatar (opcional)
     * @param cuadros    Cantidad inicial de cuadros
     */
    public TarjetaJugador(String nombre, Color color, Icon iconAvatar, int cuadros) {
        this.colorJugador = color;
        this.esSuTurno = false;

        setLayout(new BorderLayout(12, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                new EmptyBorder(12, 14, 12, 14)
        ));
        setPreferredSize(new Dimension(320, 90));


        lblAvatar = new JLabel();
        lblAvatar.setPreferredSize(new Dimension(60, 60));
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);

        if (iconAvatar != null) {
            lblAvatar.setIcon(iconAvatar);
        } else {
            lblAvatar.setOpaque(true);
            lblAvatar.setBackground(colorJugador);
            lblAvatar.setForeground(new Color(60, 64, 72));
            lblAvatar.setText(nombre.isEmpty() ? "?" : String.valueOf(nombre.charAt(0)).toUpperCase());
            lblAvatar.setFont(new Font("SansSerif", Font.BOLD, 22));
        }


        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.Y_AXIS));
        pnlCentro.setOpaque(false);

        lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblNombre.setForeground(new Color(30, 32, 38));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        pnlIndicadorTurno = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.dispose();
            }
        };
        pnlIndicadorTurno.setOpaque(false);
        pnlIndicadorTurno.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlIndicadorTurno.setMaximumSize(new Dimension(95, 22));

        lblTextoTurno = new JLabel("SU TURNO");
        lblTextoTurno.setFont(new Font("SansSerif", Font.BOLD, 10));
        pnlIndicadorTurno.add(lblTextoTurno);

        pnlCentro.add(Box.createVerticalGlue());
        pnlCentro.add(lblNombre);
        pnlCentro.add(Box.createVerticalStrut(6));
        pnlCentro.add(pnlIndicadorTurno);
        pnlCentro.add(Box.createVerticalGlue());

        JPanel pnlCuadros = new JPanel();
        pnlCuadros.setLayout(new BoxLayout(pnlCuadros, BoxLayout.Y_AXIS));
        pnlCuadros.setOpaque(false);

        JLabel lblTituloCuadros = new JLabel("CUADROS");
        lblTituloCuadros.setFont(new Font("SansSerif", Font.BOLD, 9));
        lblTituloCuadros.setForeground(new Color(120, 125, 138));
        lblTituloCuadros.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblContadorCuadros = new JLabel(String.valueOf(cuadros));
        lblContadorCuadros.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblContadorCuadros.setForeground(new Color(20, 22, 28)); // Numero oscuro
        lblContadorCuadros.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlCuadros.add(Box.createVerticalGlue());
        pnlCuadros.add(lblTituloCuadros);
        pnlCuadros.add(Box.createVerticalStrut(2));
        pnlCuadros.add(lblContadorCuadros);
        pnlCuadros.add(Box.createVerticalGlue());

        // Ensamblado
        add(lblAvatar, BorderLayout.WEST);
        add(pnlCentro, BorderLayout.CENTER);
        add(pnlCuadros, BorderLayout.EAST);

        actualizarEstadoTurno();
    }



    public void setEsSuTurno(boolean esSuTurno) {
        this.esSuTurno = esSuTurno;
        actualizarEstadoTurno();
    }

    public boolean isEsSuTurno() {
        return esSuTurno;
    }

    public void setContadorCuadros(int cantidad) {
        lblContadorCuadros.setText(String.valueOf(cantidad));
    }

    public void setNombre(String nombre) {
        lblNombre.setText(nombre);
    }

    public void setColorJugador(Color color) {
        this.colorJugador = color;
        actualizarEstadoTurno();
    }

    private void actualizarEstadoTurno() {
        if (esSuTurno) {
            pnlIndicadorTurno.setVisible(true);
            pnlIndicadorTurno.setBackground(colorJugador);
            // Seleccion automatica de contraste de texto para el badge
            lblTextoTurno.setForeground(esColorClaro(colorJugador) ? Color.BLACK : Color.WHITE);
        } else {
            pnlIndicadorTurno.setVisible(false);
        }
        revalidate();
        repaint();
    }

    private boolean esColorClaro(Color color) {
        double luminancia = (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255;
        return luminancia > 0.6;
    }
}
