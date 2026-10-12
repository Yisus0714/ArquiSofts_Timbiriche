package vista.abandono;

import vista.votacion.PanelVotacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Ventana de confirmación cuando un jugador quiere abandonar la partida en curso.
 *
 */
public class DialogoConfirmarAbandono extends JDialog {

    private final JButton btnConfirmar = PanelVotacion.crearBoton("Sí, abandonar", PanelVotacion.PELIGRO);
    private final JButton btnCancelar = PanelVotacion.crearBoton("Cancelar", PanelVotacion.SECUNDARIO);


    public DialogoConfirmarAbandono(Frame padre) {
        super(padre, "Abandonar partida", true);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(new Color(245, 247, 252));
        contenido.setBorder(new EmptyBorder(26, 36, 24, 36));

        JLabel lblIcono = new JLabel(PanelVotacion.crearIcono(
                "alerta", new Color(229, 45, 50), new Color(253, 232, 232)));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel("¿Abandonar la partida?");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(18, 24, 38));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMensaje = new JLabel("<html><div style='text-align:center; width:280px'>"
                + "Si abandonas, perderás tus cuadros y puntos. "
                + "La partida continuará con los demás jugadores.</div></html>");
        lblMensaje.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblMensaje.setForeground(new Color(100, 110, 125));
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        pnlBotones.setOpaque(false);
        pnlBotones.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnConfirmar);

        contenido.add(lblIcono);
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(lblTitulo);
        contenido.add(Box.createVerticalStrut(8));
        contenido.add(lblMensaje);
        contenido.add(Box.createVerticalStrut(22));
        contenido.add(pnlBotones);

        setContentPane(contenido);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(padre);

        // TODO (Controlador): btnConfirmar.addActionListener(...) -> notificar abandono al dominio
        // TODO (Controlador): btnCancelar.addActionListener(...)  -> cerrar el diálogo y seguir jugando
    }

    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void cerrar() {
        dispose();
    }
}