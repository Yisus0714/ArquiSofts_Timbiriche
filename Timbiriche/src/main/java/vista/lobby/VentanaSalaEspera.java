
package vista.lobby;

import vista.registro.EsquemaColores;
import vista.registro.VentanaRegistroPartida;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Simulación visual de la sala de espera.
 *
 * Permite demostrar el inicio por confirmación unánime
 * con 2 o 3 jugadores y el inicio automático con 4.
 *
 * No implementa comunicación LAN ni inicia el juego real.
 */
public class VentanaSalaEspera extends JFrame {

    private final List<JugadorLobbyDTO> jugadores;
    private final JPanel panelTarjetas;

    private final JLabel lblContador;
    private final JLabel lblSubtitulo;

    private final JButton btnSolicitarInicio;
    private final JButton btnAgregarJugador;

    private boolean partidaIniciada = false;
    private final EsquemaColores esquemaColores;

    public VentanaSalaEspera(List<JugadorLobbyDTO> jugadores, EsquemaColores esquemaColores) {
        super("Timbiriche - Sala de espera");

        if (jugadores == null
                || jugadores.size() < 1
                || jugadores.size() > 4) {
            throw new IllegalArgumentException(
                    "El lobby admite entre 1 y 4 jugadores."
            );
        }

        this.jugadores = new ArrayList<>(jugadores);
        this.esquemaColores = java.util.Objects.requireNonNull(
                esquemaColores,
                "El esquema de colores es obligatorio."
        );

        long jugadoresLocales = this.jugadores.stream()
                .filter(JugadorLobbyDTO::isJugadorLocal)
                .count();

        if (jugadoresLocales != 1) {
            throw new IllegalArgumentException(
                    "La simulación requiere un jugador local."
            );
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 560);
        setMinimumSize(new Dimension(720, 480));
        setLocationRelativeTo(null);

        JPanel contenido = new JPanel(new BorderLayout(0, 25));
        contenido.setBackground(new Color(245, 247, 252));
        contenido.setBorder(new EmptyBorder(30, 35, 30, 35));

        // Encabezado
        JPanel encabezado = new JPanel();
        encabezado.setOpaque(false);
        encabezado.setLayout(
                new BoxLayout(encabezado, BoxLayout.Y_AXIS)
        );

        JLabel titulo = new JLabel("Sala de Espera");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSubtitulo = new JLabel(
                "Esperando el inicio de la partida."
        );
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblContador = new JLabel();
        lblContador.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblContador.setForeground(new Color(47, 101, 232));
        lblContador.setAlignmentX(Component.CENTER_ALIGNMENT);

        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(8));
        encabezado.add(lblSubtitulo);
        encabezado.add(Box.createVerticalStrut(15));
        encabezado.add(lblContador);

        contenido.add(encabezado, BorderLayout.NORTH);

        // Tarjetas
        panelTarjetas = new JPanel();
        panelTarjetas.setOpaque(false);
        contenido.add(panelTarjetas, BorderLayout.CENTER);

        // Acciones de demostración
        JPanel panelAcciones = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 5)
        );
        panelAcciones.setOpaque(false);

        btnSolicitarInicio = new JButton("Solicitar inicio");
        btnSolicitarInicio.setBackground(new Color(47, 101, 232));
        btnSolicitarInicio.setForeground(Color.WHITE);
        btnSolicitarInicio.setFocusPainted(false);
        btnSolicitarInicio.addActionListener(
                e -> solicitarInicio()
        );

        btnAgregarJugador = new JButton("Simular 4.º jugador");
        btnAgregarJugador.addActionListener(
                e -> agregarJugadorSimulado()
        );

        JButton btnAbandonar = new JButton("Abandonar partida");
        btnAbandonar.addActionListener(e -> {
            dispose();
            new VentanaRegistroPartida().setVisible(true);
        });

        panelAcciones.add(btnSolicitarInicio);
        panelAcciones.add(btnAgregarJugador);
        panelAcciones.add(btnAbandonar);

        contenido.add(panelAcciones, BorderLayout.SOUTH);

        setContentPane(contenido);

        actualizarVista();

        // Si se abre directamente con cuatro participantes,
        // también debe simularse el inicio automático.
        if (this.jugadores.size() == 4) {
            SwingUtilities.invokeLater(
                    () -> iniciarPartidaSimulada(true)
            );
        }
    }

    private void actualizarVista() {
        // El jugador local siempre aparece primero.
        List<JugadorLobbyDTO> ordenados =
                new ArrayList<>(jugadores);

        ordenados.sort((a, b) ->
                Boolean.compare(
                        b.isJugadorLocal(),
                        a.isJugadorLocal()
                )
        );

        panelTarjetas.removeAll();
        panelTarjetas.setLayout(
                new GridLayout(1, ordenados.size(), 12, 0)
        );

        for (JugadorLobbyDTO jugador : ordenados) {
            panelTarjetas.add(
                    new TarjetaJugadorLobby(jugador)
            );
        }

        lblContador.setText(
                "Jugadores registrados: "
                        + jugadores.size() + " / 4"
        );

        btnSolicitarInicio.setEnabled(
                !partidaIniciada
                        && jugadores.size() >= 2
                        && jugadores.size() <= 3
        );

        btnAgregarJugador.setEnabled(
                !partidaIniciada
                        && jugadores.size() == 3
        );

        panelTarjetas.revalidate();
        panelTarjetas.repaint();
    }

    private void solicitarInicio() {
        if (partidaIniciada
                || jugadores.size() < 2
                || jugadores.size() > 3) {
            return;
        }

        Set<Integer> participantes = new HashSet<>();

        for (JugadorLobbyDTO jugador : jugadores) {
            participantes.add(jugador.getId());
        }

        VotacionInicio votacion =
                new VotacionInicio(participantes);

        // El solicitante acepta automáticamente su
        // propia propuesta de inicio.
        JugadorLobbyDTO local = jugadores.stream()
                .filter(JugadorLobbyDTO::isJugadorLocal)
                .findFirst()
                .orElseThrow();

        votacion.registrarVoto(local.getId(), true);

        // Cada diálogo representa la respuesta ficticia
        // de un jugador remoto.
        for (JugadorLobbyDTO jugador : jugadores) {
            if (jugador.isJugadorLocal()) {
                continue;
            }

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    jugador.getNombre()
                            + ": ¿Aceptas iniciar la partida?",
                    "Simulación de votación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            boolean acepta =
                    respuesta == JOptionPane.YES_OPTION;

            votacion.registrarVoto(
                    jugador.getId(), acepta
            );

            if (votacion.fueRechazada()) {
                break;
            }
        }

        if (votacion.todosAceptaron()) {
            iniciarPartidaSimulada(false);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No hubo unanimidad.\n"
                            + "La partida permanece en el lobby.",
                    "Inicio no aprobado",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void agregarJugadorSimulado() {
        if (partidaIniciada || jugadores.size() != 3) {
            return;
        }

        jugadores.add(new JugadorLobbyDTO(
                4,
                "Mateo",
                "Titán",
                esquemaColores.getColores()[3],
                false,
                false
        ));

        actualizarVista();

        // Regla del proyecto: al ingresar el cuarto
        // participante, inicia automáticamente.
        iniciarPartidaSimulada(true);
    }

    private void iniciarPartidaSimulada(boolean automatico) {
        if (partidaIniciada) {
            return;
        }

        partidaIniciada = true;

        lblSubtitulo.setText(
                "Condiciones de inicio cumplidas."
        );

        actualizarVista();

        String motivo = automatico
                ? "Se registró el cuarto jugador."
                : "Todos los participantes aceptaron.";

        JOptionPane.showMessageDialog(
                this,
                motivo + "\n"
                        + "La partida comenzaría ahora.\n\n"
                        + "Demostración visual: "
                        + "el juego real no se inicia.",
                "Inicio de partida",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
