
package vista.lobby;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Tarjeta visual de un participante en la sala de espera.
 * No contiene reglas de juego ni modifica el DTO.
 */
public class TarjetaJugadorLobby extends JPanel {

    private final JLabel lblEstado;
    private final JugadorLobbyDTO jugador;

    public TarjetaJugadorLobby(JugadorLobbyDTO jugador) {
        this.jugador = java.util.Objects.requireNonNull(jugador);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new CompoundBorder(
                new LineBorder(new Color(228, 233, 242), 1, true),
                new EmptyBorder(18, 12, 18, 12)
        ));
        setPreferredSize(new Dimension(160, 190));

        // Avatar provisional, representado por un símbolo.
        JLabel lblAvatar = new JLabel(simboloAvatar(jugador.getAvatar()));
        lblAvatar.setFont(new Font("SansSerif", Font.BOLD, 27));
        lblAvatar.setForeground(jugador.getColor());
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblNombre = new JLabel(jugador.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblNombre.setForeground(new Color(28, 40, 58));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblLocal = new JLabel(jugador.isJugadorLocal() ? "TÚ" : " ");
        lblLocal.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblLocal.setForeground(new Color(47, 101, 232));
        lblLocal.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblEstado = new JLabel();
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEstado.setOpaque(true);
        lblEstado.setBorder(new EmptyBorder(7, 12, 7, 12));

        add(Box.createVerticalGlue());
        add(lblAvatar);
        add(Box.createVerticalStrut(12));
        add(lblNombre);
        add(Box.createVerticalStrut(4));
        add(lblLocal);
        add(Box.createVerticalStrut(12));
        add(lblEstado);
        add(Box.createVerticalGlue());

        actualizarEstado();
    }

    /**
     * Actualiza el estado visual de membresía del jugador.
     *
     * En esta demostración, todo jugador representado
     * en el lobby ya está registrado.
     */
    public void actualizarEstado() {
        lblEstado.setText("✓ Registrado");
        lblEstado.setForeground(new Color(28, 87, 61));
        lblEstado.setBackground(new Color(214, 243, 225));

        revalidate();
        repaint();
    }

    private String simboloAvatar(String avatar) {
        switch (avatar) {
            case "Astro":
                return "★";
            case "Zorro":
                return "◆";
            case "Halcón":
                return "▲";
            case "Titán":
                return "⬟";
            default:
                return "?";
        }
    }
}
