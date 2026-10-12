
package vista.registro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import vista.lobby.JugadorLobbyDTO;
import vista.lobby.VentanaSalaEspera;

import java.util.ArrayList;
import java.util.List;

public class VentanaRegistroPartida extends JFrame {

    private final JTextField txtNombre;
    private final JComboBox<String> cmbAvatar;
    private final JComboBox<EsquemaColores> cmbEsquema;
    private final JButton btnIngresar;

    public VentanaRegistroPartida() {
        super("Timbiriche - Registro de jugador");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 620);
        setMinimumSize(new Dimension(520, 500));
        setLocationRelativeTo(null);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(new EmptyBorder(28, 45, 28, 45));
        contenido.setBackground(new Color(245, 247, 252));

        JLabel titulo = new JLabel("Personaliza tu Jugador");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "Configura tu perfil antes de unirte a la partida."
        );
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(8));
        contenido.add(subtitulo);
        contenido.add(Box.createVerticalStrut(35));

        contenido.add(crearEtiqueta("Nombre de jugador"));

        txtNombre = new JTextField();
        txtNombre.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, 38
        ));
        contenido.add(txtNombre);
        contenido.add(Box.createVerticalStrut(25));

        contenido.add(crearEtiqueta("Avatar"));

        cmbAvatar = new JComboBox<>(new String[]{
                "Astro",
                "Zorro",
                "Halcón",
                "Titán"
        });
        cmbAvatar.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, 38
        ));
        contenido.add(cmbAvatar);
        contenido.add(Box.createVerticalStrut(25));

        contenido.add(crearEtiqueta("Esquema de colores"));

        cmbEsquema = new JComboBox<>(EsquemaColores.values());
        cmbEsquema.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, 38
        ));
        contenido.add(cmbEsquema);
        contenido.add(Box.createVerticalStrut(40));

        btnIngresar = new JButton("Ingresar a la partida");
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, 42
        ));
        btnIngresar.setBackground(new Color(47, 101, 232));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);

        contenido.add(btnIngresar);

        btnIngresar.addActionListener(e -> ingresarALobby());

        setContentPane(contenido);
    }


    private void ingresarALobby() {

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes introducir un nombre de jugador.",
                    "Nombre obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );
            txtNombre.requestFocusInWindow();
            return;
        }

        String avatar = (String) cmbAvatar.getSelectedItem();
        EsquemaColores esquema =
                (EsquemaColores) cmbEsquema.getSelectedItem();

        if (avatar == null || esquema == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un avatar y un esquema de colores.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Color[] colores = esquema.getColores();

        // Jugadores simulados para probar el inicio anticipado.
// La entrada real por LAN queda fuera de esta demostración.
        List<JugadorLobbyDTO> jugadores = new ArrayList<>();

        jugadores.add(new JugadorLobbyDTO(
                1, nombre, avatar, colores[0], true, false
        ));

        jugadores.add(new JugadorLobbyDTO(
                2, "Ramsés", "Zorro", colores[1], false, false
        ));

        jugadores.add(new JugadorLobbyDTO(
                3, "Elena", "Halcón", colores[2], false, false
        ));

        VentanaSalaEspera lobby =
                new VentanaSalaEspera(jugadores, esquema);
        lobby.setVisible(true);

        dispose();
    }


    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 13));
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        etiqueta.setBorder(new EmptyBorder(0, 0, 8, 0));
        return etiqueta;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaRegistroPartida ventana = new VentanaRegistroPartida();
            ventana.setVisible(true);
        });
    }

}
