package View;

import Control.EjercerTurnoControl;
import Model.IConsultaJugadores;
import Model.IConsultaTablero;
import Model.IConsultaVotacion;
import Model.ModeloJuego;
import Model.ObserverModeloJuego;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VistaJuego extends JFrame implements ObserverModeloJuego {

    private static final String PANTALLA_JUEGO = "JUEGO";
    private static final String PANTALLA_ABANDONO = "ABANDONO";
    private static final String PANTALLA_VOTACION = "VOTACION";

    private final ModeloJuego modelo;
    private final EjercerTurnoControl control;
    private final PanelJugadores panelJugadores;
    private final TableroPanel tableroPanel;
    private final PanelVotacion panelVotacion;
    private final CardLayout distribucionPantallas = new CardLayout();
    private final JPanel pnlPantallas = new JPanel(distribucionPantallas);

    public VistaJuego(ModeloJuego modelo, EjercerTurnoControl control, int dimensionTablero) {
        this.modelo = modelo;
        this.control = control;

        setTitle("Timbiriche - Partida en Curso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Ocupar la mayor resolución posible
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(248, 250, 252));

        // Inicializar componentes principales con sus respectivas interfaces de consulta
        this.panelJugadores = new PanelJugadores((IConsultaJugadores) modelo);
        this.tableroPanel = new TableroPanel((IConsultaTablero) modelo, control, dimensionTablero);
        this.panelVotacion = new PanelVotacion((IConsultaVotacion) modelo, control, this::mostrarJuego);

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 250, 252));
        mainPanel.setBorder(new EmptyBorder(15, 25, 25, 25));

        // --- ENCABEZADO SUPERIOR ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(248, 250, 252));
        pnlHeader.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Logo / Identificador de juego
        JPanel pnlLogo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlLogo.setOpaque(false);

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
        pnlLogo.add(badgeLogo);

        // Botones de acción superior (Proponer terminar y Abandonar)
        JPanel pnlAccionesTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlAccionesTop.setOpaque(false);

        JButton btnProponerTerminar = new JButton("Proponer terminar");
        btnProponerTerminar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnProponerTerminar.setForeground(new Color(51, 65, 85));
        btnProponerTerminar.setBackground(Color.WHITE);
        btnProponerTerminar.setFocusPainted(false);
        btnProponerTerminar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1, true),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        btnProponerTerminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProponerTerminar.addActionListener(e -> {
            try {
                control.solicitarVotacion();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnAbandonar = new JButton("Abandonar partida");
        btnAbandonar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnAbandonar.setForeground(new Color(220, 38, 38));
        btnAbandonar.setBackground(new Color(254, 242, 242));
        btnAbandonar.setFocusPainted(false);
        btnAbandonar.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btnAbandonar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAbandonar.addActionListener(e -> distribucionPantallas.show(pnlPantallas, PANTALLA_ABANDONO));

        pnlAccionesTop.add(btnProponerTerminar);
        pnlAccionesTop.add(btnAbandonar);

        pnlHeader.add(pnlLogo, BorderLayout.WEST);
        pnlHeader.add(pnlAccionesTop, BorderLayout.EAST);

        // --- CONTENEDOR CENTRAL ---
        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.Y_AXIS));
        pnlCentro.setBackground(new Color(248, 250, 252));

        pnlCentro.add(panelJugadores);
        pnlCentro.add(Box.createVerticalStrut(15));
        
        // Contenedor para mantener el tablero centrado y con tamaño óptimo
        JPanel pnlTableroWrapper = new JPanel(new GridBagLayout());
        pnlTableroWrapper.setBackground(new Color(248, 250, 252));
        pnlTableroWrapper.add(tableroPanel);
        pnlCentro.add(pnlTableroWrapper);

        mainPanel.add(pnlHeader, BorderLayout.NORTH);
        mainPanel.add(pnlCentro, BorderLayout.CENTER);

        // El juego, la confirmación de abandono y la votación comparten la ventana
        pnlPantallas.add(mainPanel, PANTALLA_JUEGO);
        pnlPantallas.add(new PanelAbandono(control, this::mostrarJuego), PANTALLA_ABANDONO);
        pnlPantallas.add(panelVotacion, PANTALLA_VOTACION);
        add(pnlPantallas);
    }

    private void mostrarJuego() {
        distribucionPantallas.show(pnlPantallas, PANTALLA_JUEGO);
    }

    // Métodos delegados a los observadores internos
    @Override
    public void notificarSeleccion() {
        panelJugadores.notificarSeleccion();
        tableroPanel.notificarSeleccion();
    }

    @Override
    public void notificarDeseleccion() {
        panelJugadores.notificarDeseleccion();
        tableroPanel.notificarDeseleccion();
    }

    @Override
    public void notificarJugada() {
        panelJugadores.notificarJugada();
        tableroPanel.notificarJugada();
    }

    @Override
    public void notificarCambioTurno() {
        panelJugadores.notificarCambioTurno();
        tableroPanel.notificarCambioTurno();
    }

    @Override
    public void notificarAbandono() {
        panelJugadores.notificarAbandono();
        tableroPanel.notificarAbandono();
    }

    @Override
    public void notificarVoto() {
        panelJugadores.notificarVoto();
        tableroPanel.notificarVoto();
        if (panelVotacion.actualizarDatos()) {
            distribucionPantallas.show(pnlPantallas, PANTALLA_VOTACION);
        } else {
            mostrarJuego();
        }
    }
}