package View;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luisb
 */
import Control.ControlRegistro;
import Dominio.Avatar;
import Dominio.ColorJugador;
import Model.EsquemaColores;
import Model.IConsultaRegistro;
import Model.ObserverModelo;
import eventos.Event;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VistaRegistro extends JFrame implements ObserverModelo {

    private static final int TAM_ICONO_AVATAR = 48;

    private static final Color BORDE_NORMAL = new Color(226, 232, 240);
    private static final Color FONDO_NORMAL = new Color(248, 250, 252);
    private static final Color BORDE_SELECCION = new Color(37, 99, 235);
    private static final Color FONDO_SELECCION = new Color(219, 234, 254);

    private final ControlRegistro control;
    private final IConsultaRegistro modelo;

    private JTextField txtNombre;
    private JPanel pnlContenedorAvatares;
    private JPanel pnlContenedorEsquemas;

    // Estado de selección
    private Avatar avatarSeleccionado;
    private EsquemaColores esquemaSeleccionado;

    public VistaRegistro(ControlRegistro control, IConsultaRegistro modelo) {
        this.modelo = modelo;
        this.control = control;
        setTitle("Personaliza tu Jugador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        initUI();

        // Cargar datos iniciales
        cargarAvataresDisponibles();
        cargarEsquemasColor();
    }

    // ------------------------------------------------ ObserverModelo

    /**
     * Reacciona a los eventos que reenvía ModeloRegistro. Cualquier registro o abandono cambia los
     * avatares libres; la ventana se muestra mientras ESTE jugador no esté registrado.
     */
    @Override
    public void actualizar(Event evento) {
        switch (evento) {
            case JUGADOR_REGISTRADO, JUGADOR_ABANDONO -> {
                cargarAvataresDisponibles();
                boolean mostrar = !modelo.estoyRegistrado();
                if (isVisible() != mostrar) {
                    setVisible(mostrar);
                }
            }
            default -> { }
        }
    }

    // ------------------------------------------------------------------ UI

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(25, 40, 25, 40));

        // --- ENCABEZADO ---
        JLabel lblBadge = new JLabel("LOBBY DE ENTRADA");
        lblBadge.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblBadge.setForeground(new Color(37, 99, 235));
        lblBadge.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Personaliza tu Jugador");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(15, 23, 42));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Configura tu perfil antes de unirte a la partida.");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(100, 116, 139));
        lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

        // --- SECCIÓN: NOMBRE ---
        JPanel pnlNombreWrap = crearSeccionContenedor("Nombre de jugador");
        
        JPanel pnlCampoCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlCampoCentro.setBackground(Color.WHITE);
        pnlCampoCentro.setAlignmentX(CENTER_ALIGNMENT);
        
        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(520, 42));
        txtNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtNombre.setBackground(new Color(241, 245, 249));
        txtNombre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1, true),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        pnlCampoCentro.add(txtNombre);
        pnlNombreWrap.add(pnlCampoCentro);

        // --- SECCIÓN: AVATAR ---
        JPanel pnlAvatarWrap = crearSeccionContenedor("Avatar");
        pnlContenedorAvatares = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        pnlContenedorAvatares.setBackground(Color.WHITE);
        pnlContenedorAvatares.setAlignmentX(CENTER_ALIGNMENT);
        pnlAvatarWrap.add(pnlContenedorAvatares);

        // --- SECCIÓN: ESQUEMA DE COLOR ---
        JPanel pnlEsquemaWrap = crearSeccionContenedor("Esquema de color");
        pnlContenedorEsquemas = new JPanel(new GridLayout(2, 2, 12, 10));
        pnlContenedorEsquemas.setBackground(Color.WHITE);
        pnlContenedorEsquemas.setAlignmentX(CENTER_ALIGNMENT);
        pnlContenedorEsquemas.setMaximumSize(new Dimension(520, 150));
        pnlEsquemaWrap.add(pnlContenedorEsquemas);

        // --- BOTÓN INGRESAR ---
        JButton btnIngresar = new JButton("Ingresar a la partida →");
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setBackground(new Color(37, 99, 235));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setPreferredSize(new Dimension(520, 45));
        btnIngresar.setMaximumSize(new Dimension(520, 45));
        btnIngresar.setAlignmentX(CENTER_ALIGNMENT);
        btnIngresar.addActionListener(e -> intentarIngreso());

        // Ensamblar todo con espaciados limpios (sin el botón de volver)
        mainPanel.add(lblBadge);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createVerticalStrut(4));
        mainPanel.add(lblSubtitulo);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(pnlNombreWrap);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(pnlAvatarWrap);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(pnlEsquemaWrap);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(btnIngresar);

        add(mainPanel);
    }

    private JPanel crearSeccionContenedor(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(520, 130));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTitulo.setForeground(new Color(51, 65, 85));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(6));

        return panel;
    }

    // ------------------------------------------------- carga de avatares

    private void cargarAvataresDisponibles() {
        Avatar seleccionPrevia = avatarSeleccionado;
        avatarSeleccionado = null;
        pnlContenedorAvatares.removeAll();

        List<Avatar> disponibles = modelo.getAvataresDisponibles();

        if (disponibles.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay avatares disponibles.");
            lblVacio.setForeground(Color.GRAY);
            pnlContenedorAvatares.add(lblVacio);
        }

        for (Avatar avatar : disponibles) {
            JPanel pnlItem = crearPanelSeleccionableAvatar(avatar);
            if (avatar == seleccionPrevia) {
                avatarSeleccionado = avatar;
                marcarSeleccionadoAvatar(pnlItem);
            }
            pnlContenedorAvatares.add(pnlItem);
        }

        pnlContenedorAvatares.revalidate();
        pnlContenedorAvatares.repaint();
    }

    private void cargarEsquemasColor() {
        pnlContenedorEsquemas.removeAll();
        for (EsquemaColores esquema : EsquemaColores.values()) {
            JPanel pnlItem = crearPanelSeleccionableEsquema(esquema);
            pnlContenedorEsquemas.add(pnlItem);
        }
        pnlContenedorEsquemas.revalidate();
        pnlContenedorEsquemas.repaint();
    }

    // --------------------------------------- manejo de selección y errores

    private void intentarIngreso() {
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de jugador no puede estar vacío.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (avatarSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un avatar para continuar.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (esquemaSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un esquema de color.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            control.manejarRegistro(nombre, avatarSeleccionado, esquemaSeleccionado);
            // Si el dominio lo acepta, el evento JUGADOR_REGISTRADO oculta esta ventana y abre el lobby.
        } catch (Exception e) {
            String detalle = (e.getMessage() != null && !e.getMessage().isBlank())
                    ? e.getMessage()
                    : "No se pudo completar el registro.";
            JOptionPane.showMessageDialog(this, detalle, "No se pudo registrar", JOptionPane.ERROR_MESSAGE);
            cargarAvataresDisponibles();
        }
    }

    // -------------------------------------- componentes visuales propios

    private JPanel crearPanelSeleccionableAvatar(Avatar avatar) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(115, 75));
        panel.setBackground(FONDO_NORMAL);
        panel.setBorder(BorderFactory.createLineBorder(BORDE_NORMAL, 1, true));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblIcon = new JLabel(RecursosAvatar.icono(avatar, TAM_ICONO_AVATAR));
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblNombre = new JLabel(avatar.name());
        lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombre.setForeground(new Color(71, 85, 105));

        panel.add(lblIcon, BorderLayout.CENTER);
        panel.add(lblNombre, BorderLayout.SOUTH);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                avatarSeleccionado = avatar;
                resetearBordesAvatares();
                marcarSeleccionadoAvatar(panel);
            }
        });

        return panel;
    }

    private void marcarSeleccionadoAvatar(JPanel panel) {
        panel.setBorder(BorderFactory.createLineBorder(BORDE_SELECCION, 2, true));
        panel.setBackground(FONDO_SELECCION);
    }

    private void resetearBordesAvatares() {
        for (Component c : pnlContenedorAvatares.getComponents()) {
            if (c instanceof JPanel) {
                ((JPanel) c).setBorder(BorderFactory.createLineBorder(BORDE_NORMAL, 1, true));
                ((JPanel) c).setBackground(FONDO_NORMAL);
            }
        }
    }

    private JPanel crearPanelSeleccionableEsquema(EsquemaColores esquema) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FONDO_NORMAL);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDE_NORMAL, 1, true),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));

        JLabel lblNombre = new JLabel(esquema.name());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblNombre.setForeground(new Color(51, 65, 85));
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblNombre, BorderLayout.NORTH);

        JPanel pnlColores = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
        pnlColores.setOpaque(false);
        for (ColorJugador cJugador : ColorJugador.values()) {
            Color c = esquema.resolver(cJugador);
            pnlColores.add(new CirculoColorPanel(c));
        }
        panel.add(pnlColores, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                esquemaSeleccionado = esquema;
                resetearBordesEsquemas();
                panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDE_SELECCION, 2, true),
                    BorderFactory.createEmptyBorder(6, 6, 6, 6)
                ));
                panel.setBackground(FONDO_SELECCION);
            }
        });

        return panel;
    }

    private void resetearBordesEsquemas() {
        for (Component c : pnlContenedorEsquemas.getComponents()) {
            if (c instanceof JPanel) {
                JPanel p = (JPanel) c;
                p.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDE_NORMAL, 1, true),
                    BorderFactory.createEmptyBorder(6, 6, 6, 6)
                ));
                p.setBackground(FONDO_NORMAL);
            }
        }
    }
}

/**
 * Componente gráfico simple para dibujar los círculos de colores de los esquemas.
 */
class CirculoColorPanel extends JPanel {

    private final Color color;

    public CirculoColorPanel(Color color) {
        this.color = color;
        setPreferredSize(new Dimension(14, 14));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillOval(0, 0, getWidth(), getHeight());
    }
}