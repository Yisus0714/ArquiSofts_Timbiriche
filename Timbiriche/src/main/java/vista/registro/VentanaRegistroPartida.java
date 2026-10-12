
package vista.registro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import vista.lobby.JugadorLobbyDTO;
import vista.lobby.VentanaSalaEspera;
import java.text.Normalizer;
import java.util.Locale;

import java.util.ArrayList;
import java.util.List;

public class VentanaRegistroPartida extends JFrame {

    private final JTextField txtNombre;
    private final ButtonGroup grupoAvatares = new ButtonGroup();
    private String avatarSeleccionado = "Astro";
    private final ButtonGroup grupoEsquemas = new ButtonGroup();
    private EsquemaColores esquemaSeleccionado = EsquemaColores.CLASICO;
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

        JPanel panelAvatares = new JPanel(
                new GridLayout(1, 4, 10, 0)
        );
        panelAvatares.setOpaque(false);
        panelAvatares.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelAvatares.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 85)
        );

        panelAvatares.add(crearBotonAvatar("Astro", "★"));
        panelAvatares.add(crearBotonAvatar("Zorro", "◆"));
        panelAvatares.add(crearBotonAvatar("Halcón", "▲"));
        panelAvatares.add(crearBotonAvatar("Titán", "⬟"));
        actualizarEstiloAvatares();

        contenido.add(panelAvatares);
        contenido.add(Box.createVerticalStrut(25));

        contenido.add(crearEtiqueta("Esquema de colores"));

        JPanel panelEsquemas = new JPanel(
                new GridLayout(2, 2, 12, 12)
        );

        panelEsquemas.setOpaque(false);
        panelEsquemas.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelEsquemas.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 150)
        );

        for (EsquemaColores esquema : EsquemaColores.values()) {
            panelEsquemas.add(crearBotonEsquema(esquema));
        }

        actualizarEstiloEsquemas();

        contenido.add(panelEsquemas);
        contenido.add(Box.createVerticalStrut(25));

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

    private JToggleButton crearBotonAvatar(
            String nombre,
            String simbolo
    ) {
        JToggleButton boton = new JToggleButton(
                "<html><center>"
                        + "<span style='font-size:23px;'>"
                        + simbolo
                        + "</span><br>"
                        + nombre
                        + "</center></html>"
        );

        boton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        boton.setFocusPainted(false);
        boton.setBackground(Color.WHITE);
        boton.setForeground(new Color(30, 40, 60));
        boton.setBorder(BorderFactory.createLineBorder(
                new Color(220, 228, 240), 1
        ));

        grupoAvatares.add(boton);

        boton.addActionListener(e -> {
            avatarSeleccionado = nombre;
            actualizarEstiloAvatares();
        });

        if (nombre.equals(avatarSeleccionado)) {
            boton.setSelected(true);
        }

        return boton;
    }

    private JToggleButton crearBotonEsquema(
            EsquemaColores esquema
    ) {
        Color[] colores = esquema.getColores();

        JToggleButton boton = new JToggleButton();
        boton.setLayout(new BorderLayout(0, 8));

        JLabel lblNombre = new JLabel(esquema.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 12));

        JPanel muestras = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 7, 0)
        );
        muestras.setOpaque(false);

        for (Color color : colores) {
            JPanel muestra = new JPanel();
            muestra.setBackground(color);
            muestra.setPreferredSize(new Dimension(18, 18));
            muestras.add(muestra);
        }

        JPanel contenidoBoton = new JPanel(
                new BorderLayout(0, 8)
        );
        contenidoBoton.setOpaque(false);
        contenidoBoton.add(lblNombre, BorderLayout.NORTH);
        contenidoBoton.add(muestras, BorderLayout.CENTER);

        boton.add(contenidoBoton);

        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 228, 240), 1
                ),
                new EmptyBorder(10, 12, 10, 12)
        ));

        grupoEsquemas.add(boton);

        if (esquema == esquemaSeleccionado) {
            boton.setSelected(true);
        }

        boton.addActionListener(e -> {
            esquemaSeleccionado = esquema;
            actualizarEstiloEsquemas();
        });

        return boton;
    }

    private void actualizarEstiloEsquemas() {
        java.util.Enumeration<AbstractButton> botones =
                grupoEsquemas.getElements();

        while (botones.hasMoreElements()) {
            AbstractButton boton = botones.nextElement();

            Color borde = boton.isSelected()
                    ? new Color(47, 101, 232)
                    : new Color(220, 228, 240);

            boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                            borde, boton.isSelected() ? 2 : 1
                    ),
                    new EmptyBorder(10, 12, 10, 12)
            ));

            boton.setBackground(
                    boton.isSelected()
                            ? new Color(220, 232, 255)
                            : Color.WHITE
            );
        }
    }


    private void actualizarEstiloAvatares() {
        java.util.Enumeration<AbstractButton> botones =
                grupoAvatares.getElements();

        while (botones.hasMoreElements()) {
            AbstractButton boton = botones.nextElement();

            if (boton.isSelected()) {
                boton.setBackground(new Color(220, 232, 255));
                boton.setBorder(BorderFactory.createLineBorder(
                        new Color(47, 101, 232), 2
                ));
            } else {
                boton.setBackground(Color.WHITE);
                boton.setBorder(BorderFactory.createLineBorder(
                        new Color(220, 228, 240), 1
                ));
            }
        }
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

        String avatar = avatarSeleccionado;
        EsquemaColores esquema = esquemaSeleccionado;

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

        // Participantes ficticios previamente registrados.
        // No representan conexiones LAN reales.
        List<JugadorLobbyDTO> jugadores = new ArrayList<>();

        jugadores.add(new JugadorLobbyDTO(
                2, "Ramsés", "Zorro", colores[1], false, false
        ));

        jugadores.add(new JugadorLobbyDTO(
                3, "Elena", "Halcón", colores[2], false, false
        ));

        // Validar contra los mismos datos que verá el Lobby.
        for (JugadorLobbyDTO registrado : jugadores) {

            if (normalizarNombre(nombre)
                    .equals(normalizarNombre(registrado.getNombre()))) {

                JOptionPane.showMessageDialog(
                        this,
                        "El nombre \"" + nombre
                                + "\" ya está registrado.",
                        "Nombre duplicado",
                        JOptionPane.WARNING_MESSAGE
                );

                txtNombre.requestFocusInWindow();
                return;
            }

            if (avatar.equals(registrado.getAvatar())) {

                JOptionPane.showMessageDialog(
                        this,
                        "El avatar \"" + avatar
                                + "\" ya está siendo utilizado.",
                        "Avatar duplicado",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        // Registrar al jugador local después de validar.
        JugadorLobbyDTO jugadorLocal = new JugadorLobbyDTO(
                1, nombre, avatar, colores[0], true, false
        );

        // El jugador local ocupa la primera posición visual.
        jugadores.add(0, jugadorLocal);

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

    private String normalizarNombre(String nombre) {
        String texto = Normalizer.normalize(
                nombre.trim(),
                Normalizer.Form.NFD
        );

        return texto
                .replaceAll("\\p{M}+", "")
                .toLowerCase(Locale.ROOT);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaRegistroPartida ventana = new VentanaRegistroPartida();
            ventana.setVisible(true);
        });
    }

}
