package View;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luisb
 */
import Control.ControlLobby;
import Dominio.Voto;
import Model.IConsultaLobby;
import Model.ObserverModelo;
import eventos.Event;
import dto.JugadorLobbyDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class VistaLobby extends JFrame implements ObserverModelo {

    private final ControlLobby control;
    private final IConsultaLobby dominio;

    private JPanel pnlContenedorJugadores;

    public VistaLobby(ControlLobby control, IConsultaLobby dominio) {
        this.control = control;
        this.dominio = dominio;

        setTitle("Sala de Espera - Timbiriche");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        initUI();
        try {
            cargarJugadores();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al cargar la sala", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ------------------------------------------------ ObserverModelo

    /**
     * Reacciona a los eventos que reenvía ModeloLobby. La ventana se muestra mientras este jugador
     * está en la sala de espera: el registro propio la abre; el abandono propio o el inicio de la
     * partida la cierran.
     */
    @Override
    public void actualizar(Event evento) {
        switch (evento) {
            case JUGADOR_REGISTRADO, JUGADOR_ABANDONO, VOTO_REGISTRADO -> {
                if (!dominio.isActivo()) {
                    setVisible(false);           // el que salió fui yo
                    return;
                }
                refrescarJugadores();
                if (!isVisible()) {
                    setVisible(true);
                }
            }
            case JUEGO_INICIADO -> setVisible(false);
            default -> { }
        }
    }

    private void refrescarJugadores() {
        try {
            cargarJugadores();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ------------------------------------------------------------------ UI

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // --- ENCABEZADO SUPERIOR (LOGO Y BOTÓN ABANDONAR) ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setMaximumSize(new Dimension(750, 45));
        pnlHeader.setAlignmentX(CENTER_ALIGNMENT);

        // Logo / Título izquierdo
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
        badgeLogo.setPreferredSize(new Dimension(28, 28));
        badgeLogo.setOpaque(false);
        JLabel lblT = new JLabel("T", SwingConstants.CENTER);
        lblT.setForeground(Color.WHITE);
        lblT.setFont(new Font("SansSerif", Font.BOLD, 14));
        badgeLogo.setLayout(new BorderLayout());
        badgeLogo.add(lblT, BorderLayout.CENTER);

        JLabel lblTim = new JLabel("TIMBIRICHE");
        lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTim.setForeground(new Color(15, 23, 42));

        pnlLogo.add(badgeLogo);
        pnlLogo.add(lblTim);

        // Botón Abandonar Partida Superior
        JButton btnAbandonarTop = new JButton("➔ ABANDONAR PARTIDA");
        btnAbandonarTop.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnAbandonarTop.setForeground(new Color(220, 38, 38));
        btnAbandonarTop.setBackground(new Color(254, 242, 242));
        btnAbandonarTop.setFocusPainted(false);
        btnAbandonarTop.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btnAbandonarTop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAbandonarTop.addActionListener(e -> {
            if (control != null) {
                try {
                    control.solicitarAbandono();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al abandonar", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pnlHeader.add(pnlLogo, BorderLayout.WEST);
        pnlHeader.add(btnAbandonarTop, BorderLayout.EAST);

        // --- TÍTULOS DE LA SALA ---
        JLabel lblBadgePartida = new JLabel("PARTIDA COMPETITIVA");
        lblBadgePartida.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblBadgePartida.setForeground(new Color(37, 99, 235));
        lblBadgePartida.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Sala de Espera");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(15, 23, 42));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Esperando a que todos los jugadores estén listos para comenzar.");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(100, 116, 139));
        lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

        // --- CONTENEDOR DE JUGADORES ---
        pnlContenedorJugadores = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlContenedorJugadores.setBackground(Color.WHITE);
        pnlContenedorJugadores.setAlignmentX(CENTER_ALIGNMENT);

        // Ensamblar elementos principales
        mainPanel.add(pnlHeader);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(lblBadgePartida);
        mainPanel.add(Box.createVerticalStrut(6));
        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createVerticalStrut(4));
        mainPanel.add(lblSubtitulo);
        mainPanel.add(Box.createVerticalStrut(35));
        mainPanel.add(pnlContenedorJugadores);

        add(mainPanel);
    }

    private void cargarJugadores() throws Exception {
        pnlContenedorJugadores.removeAll();

        List<JugadorLobbyDTO> jugadores = dominio.getJugadores();

        if (jugadores == null || jugadores.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay jugadores conectados en la sala.");
            lblVacio.setForeground(Color.GRAY);
            pnlContenedorJugadores.add(lblVacio);
        } else {
            for (JugadorLobbyDTO jugador : jugadores) {
                pnlContenedorJugadores.add(crearTarjetaJugador(jugador));
            }
        }

        pnlContenedorJugadores.revalidate();
        pnlContenedorJugadores.repaint();
    }

    // -------------------------------------- TARJETA INDIVIDUAL DE JUGADOR

    private JPanel crearTarjetaJugador(JugadorLobbyDTO dto) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setPreferredSize(new Dimension(160, 210));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
            BorderFactory.createEmptyBorder(20, 10, 20, 10)
        ));

        // Círculo / Avatar superior con el color del jugador
        // Avatar: imagen del enum dentro de un círculo con el color del jugador
        final int TAM_AVATAR = 72;
        final int TAM_ICONO = 48;
        final Color colorJugador = dto.getColor() != null ? dto.getColor() : new Color(37, 99, 235);
        final javax.swing.ImageIcon iconoAvatar = RecursosAvatar.icono(dto.getAvatar(), TAM_ICONO);

        JPanel pnlAvatarCirculo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // Fondo: el color del jugador, suavizado, para que el avatar destaque
                g2.setColor(new Color(colorJugador.getRed(), colorJugador.getGreen(), colorJugador.getBlue(), 45));
                g2.fillOval(0, 0, w - 1, h - 1);

                // Aro con el color característico del jugador
                g2.setColor(colorJugador);
                g2.setStroke(new BasicStroke(3f));
                g2.drawOval(2, 2, w - 5, h - 5);

                // Imagen del avatar, centrada
                iconoAvatar.paintIcon(this, g2, (w - TAM_ICONO) / 2, (h - TAM_ICONO) / 2);

                g2.dispose();
            }
        };
        pnlAvatarCirculo.setPreferredSize(new Dimension(TAM_AVATAR, TAM_AVATAR));
        pnlAvatarCirculo.setMinimumSize(new Dimension(TAM_AVATAR, TAM_AVATAR));
        pnlAvatarCirculo.setMaximumSize(new Dimension(TAM_AVATAR, TAM_AVATAR));
        pnlAvatarCirculo.setOpaque(false);
        pnlAvatarCirculo.setAlignmentX(CENTER_ALIGNMENT);

        // Nombre y etiqueta "TÚ" si es el usuario actual
        JPanel pnlNombreContenedor = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pnlNombreContenedor.setOpaque(false);
        pnlNombreContenedor.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblNombre = new JLabel(dto.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombre.setForeground(new Color(15, 23, 42));
        pnlNombreContenedor.add(lblNombre);

        if (dto.isEsTu()) {

            JLabel lblTu = new JLabel("TÚ");
            lblTu.setFont(new Font("SansSerif", Font.BOLD, 9));
            lblTu.setForeground(textoLegibleSobre(colorJugador));
            lblTu.setOpaque(true);
            lblTu.setBackground(colorJugador);
            lblTu.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
            pnlNombreContenedor.add(lblTu);
        }
        
        

        // Botón de estado (Listo / Esperando)
        JButton btnEstado = new JButton();
        btnEstado.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnEstado.setAlignmentX(CENTER_ALIGNMENT);
        btnEstado.setPreferredSize(new Dimension(130, 34));
        btnEstado.setMaximumSize(new Dimension(130, 34));
        btnEstado.setFocusPainted(false);

        if (dto.isIsReady()) {
            btnEstado.setText("✓ Listo ✓");
            btnEstado.setBackground(new Color(220, 252, 231)); // Verde claro suave
            btnEstado.setForeground(new Color(22, 101, 52));
        } else {
            btnEstado.setText("🕒 Esperando...");
            btnEstado.setBackground(new Color(241, 245, 249)); // Gris claro suave
            btnEstado.setForeground(new Color(71, 85, 105));
        }

        // REQUISITO: Si no es tuyo (esTu == false), el botón no debe poder clickearse.
        if (!dto.isEsTu()) {
            btnEstado.setEnabled(false);
            btnEstado.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            btnEstado.setCursor(new Cursor(Cursor.HAND_CURSOR));
            // Asignación de acciones según su estado actual (isReady)
            btnEstado.addActionListener(e -> {
                if (dto.isIsReady()) {
                    if (control != null) {
                        try {
                            control.quitarVoto();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    if (control != null) {
                        try {
                            control.votar(Voto.FAVOR);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        }

        // Ensamblar tarjeta
        tarjeta.add(pnlAvatarCirculo);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(pnlNombreContenedor);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(btnEstado);

        return tarjeta;
    }
    
    private static Color textoLegibleSobre(Color fondo) {
        double luminancia = (0.299 * fondo.getRed()
                           + 0.587 * fondo.getGreen()
                           + 0.114 * fondo.getBlue()) / 255.0;
        return luminancia > 0.6 ? new Color(15, 23, 42) : Color.WHITE;
    }
    
}